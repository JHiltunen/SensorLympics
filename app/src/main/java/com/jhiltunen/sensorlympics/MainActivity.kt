package com.jhiltunen.sensorlympics

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jhiltunen.sensorlympics.magnetgame.chooseDirection
import com.jhiltunen.sensorlympics.navigator.MainAppNav
import com.jhiltunen.sensorlympics.pressuregame.PressureViewModel
import com.jhiltunen.sensorlympics.pressuregame.PressureViewModelProgress
import com.jhiltunen.sensorlympics.receivers.AirPlaneModeReceiver
import com.jhiltunen.sensorlympics.services.MusicService
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme
import com.jhiltunen.sensorlympics.utils.basicNotificationTapAction
import com.jhiltunen.sensorlympics.utils.createNotificationChannel
import com.jhiltunen.sensorlympics.viewmodels.BallGameViewModel
import com.jhiltunen.sensorlympics.viewmodels.MagnetViewModel
import com.jhiltunen.sensorlympics.viewmodels.ReceiverViewModel
import com.jhiltunen.sensorlympics.viewmodels.ScoreViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig

@ExperimentalFoundationApi
class MainActivity : ComponentActivity(), SensorEventListener {
    companion object {
        val magnetViewModel = MagnetViewModel()
        val pressureViewModel = PressureViewModel()
        val pressureViewModelProgress = PressureViewModelProgress()
        val ballGameViewModel = BallGameViewModel()
        val receiverViewModel = ReceiverViewModel()
        lateinit var scoreViewModel: ScoreViewModel
        var pressureSensorExists = true
        var magnetometerSensorExists = true
        var accelerometerSensorExists = true
    }

    private var boilingPoint: Float = 100.0F
    private var min: Float = 0.0F
    private var max: Float = 0.0F
    private var heightDifference: Float = 0.0F

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var magnetometer: Sensor
    private var pressure: Sensor? = null


    private var currentDegree = 0.0f
    private var lastAccelerometer = FloatArray(3)
    private var lastMagnetometer = FloatArray(3)
    private var lastAccelerometerSet = false
    private var lastMagnetometerSet = false

    private lateinit var receiver: AirPlaneModeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoreViewModel = ScoreViewModel(application)
        ballGameViewModel.setMaxValues(
            getScreenDimensions(this)[0].toFloat() - 200,
            getScreenDimensions(this)[1].toFloat() - 100
        )

        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID

        if ((ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
                    PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorsExists()

        if (accelerometerSensorExists) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }

        if (magnetometerSensorExists) {
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        }

        if (pressureSensorExists) {
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        }
        magnetViewModel.upDateWin(0)
        chooseDirection()

        setContent {
            var musicPori by remember { mutableStateOf(false) }
            val context = LocalContext.current
            val notificationT = stringResource(R.string.noti_1_title)
            val notificationM = stringResource(R.string.noti_1_message)
            val notificationM2 = stringResource(R.string.noti_2)

            val channelId = "Spock"
            val notificationId = 0

            LaunchedEffect(Unit) {
                createNotificationChannel(channelId, context)
            }
            SensorLympicsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .clickable(onClick = {
                                if (!musicPori) {
                                    startService(
                                        Intent(
                                            applicationContext,
                                            MusicService::class.java
                                        )
                                    )
                                    basicNotificationTapAction(
                                        context,
                                        channelId,
                                        notificationId,
                                        notificationT,
                                        notificationM
                                    )
                                    musicPori = true
                                } else {
                                    stopService(
                                        Intent(
                                            applicationContext,
                                            MusicService::class.java
                                        )
                                    )
                                    basicNotificationTapAction(
                                        context,
                                        channelId,
                                        notificationId,
                                        notificationT,
                                        notificationM2
                                    )
                                    musicPori = false
                                }
                            })
                    )
                    {
                        MainAppNav()
                    }
                }
            }
        }
        //for airplane mode receiver
        receiver = AirPlaneModeReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }
        checkAirplaneMode()
    }

    private fun checkAirplaneMode() {
        if (isAirplaneModeOn(applicationContext)) {
            receiverViewModel.updateAirplane(true)
        } else {
            receiverViewModel.updateAirplane(false)
        }
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.System.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    }

    override fun onResume() {
        super.onResume()
        if (accelerometerSensorExists) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        }

        if (magnetometerSensorExists) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_FASTEST)
        }

        if (pressureSensorExists) {
            sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        if (accelerometerSensorExists) {
            sensorManager.unregisterListener(this, accelerometer)
        }

        if (magnetometerSensorExists) {
            sensorManager.unregisterListener(this, magnetometer)
        }

        if (pressureSensorExists) {
            sensorManager.unregisterListener(this, pressure)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    @Suppress("DEPRECATION")
    private fun getScreenDimensions(activity: Activity): IntArray {
        val dimensions = IntArray(2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            dimensions[0] = windowMetrics.bounds.width() - insets.left - insets.right
            dimensions[1] = windowMetrics.bounds.height() - insets.bottom - insets.top
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            dimensions[0] = displayMetrics.widthPixels
            dimensions[1] = displayMetrics.heightPixels
        }
        return dimensions
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == accelerometer) {
            ballGameViewModel.updateXAcceleration(event.values[0])
            ballGameViewModel.updateYAcceleration(-event.values[1])

            ballGameViewModel.updateBall()
        }

        if (event.sensor === accelerometer) {
            lowPass(event.values, lastAccelerometer)
            lastAccelerometerSet = true
        } else if (event.sensor === magnetometer) {
            magnetViewModel.updateValue(
                getString(
                    R.string.sensor_val,
                    event.values[0],
                    event.values[1],
                    event.values[2]
                ), event.values[0], event.values[1]
            )
            lowPass(event.values, lastMagnetometer)
            lastMagnetometerSet = true
        }

        if (lastAccelerometerSet && lastMagnetometerSet) {
            val r = FloatArray(9)
            if (SensorManager.getRotationMatrix(r, null, lastAccelerometer, lastMagnetometer)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(r, orientation)
                val degree = (Math.toDegrees(orientation[0].toDouble()) + 360).toFloat() % 360
                currentDegree = degree
                magnetViewModel.upDateDegree(currentDegree)
            }
        }

        if (event.sensor == pressure) {

            if (min == 0.0F) {
                min = event.values[0]
            }

            if (min > event.values[0]) {
                min = event.values[0]
            }

            if (max == 0.0F) {
                max = event.values[0]
            }

            if (max < event.values[0]) {
                max = event.values[0]
            }
            boilingPoint = (100 - ((1013.25 - event.values[0]) / 19.05) * 0.5).toFloat()

            heightDifference = ((max - min) * 8)

            pressureViewModel.updateValue(

                getString(
                    R.string.sensor_val3,
                    event.values[0],
                    boilingPoint,
                    max,
                    min,
                    heightDifference
                )
            )
            pressureViewModelProgress.updateValue(event.values[0], max, min)
        }

    }

    private fun lowPass(input: FloatArray, output: FloatArray) {
        val alpha = 0.05f

        for (i in input.indices) {
            output[i] = output[i] + alpha * (input[i] - output[i])
        }
    }

    private fun sensorsExists() =
        (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null).also {
            pressureSensorExists = it
        } && (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null).also {
            magnetometerSensorExists = it
        } && (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null).also {
            accelerometerSensorExists = it
        }
}