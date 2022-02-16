package com.jhiltunen.sensorlympics

import com.jhiltunen.sensorlympics.magnetgame.MagnetViewModel
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jhiltunen.sensorlympics.magnetgame.chooseDirection
import com.jhiltunen.sensorlympics.magnetgame.readFile
import com.jhiltunen.sensorlympics.navigator.MainAppNav
import com.jhiltunen.sensorlympics.pressuregame.PressureViewModel
import com.jhiltunen.sensorlympics.pressuregame.PressureViewModelProgress
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme

internal const val FILENAMEMAGNET = "magnetHighScore.txt"


class MainActivity : ComponentActivity(), SensorEventListener {

    companion object {
        val magnetViewModel = MagnetViewModel()
        val pressureViewModel = PressureViewModel()
        val pressureViewModelProgress = PressureViewModelProgress()
    }

    var boilingPoint: Float = 100.0F
    var min: Float = 0.0F
    var max: Float = 0.0F
    var heightDifference: Float = 0.0F


    lateinit var sensorManager: SensorManager
    lateinit var accelerometer: Sensor
    lateinit var magnetometer: Sensor
    lateinit var pressure: Sensor


    var currentDegree = 0.0f
    var lastAccelerometer = FloatArray(3)
    var lastMagnetometer = FloatArray(3)
    var lastAccelerometerSet = false
    var lastMagnetometerSet = false


    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        magnetViewModel.upDateWin(0)
        chooseDirection()

        readFile(application)
        /*
        val readMagnetFile = readFile(application)
        val scoreHigh = readMagnetFile.toString().drop(1).dropLast(1).toInt()
        magnetViewModel.upDateScore(scoreHigh.toFloat())
         */
        setContent {
            SensorLympicsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainAppNav()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_FASTEST)
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, accelerometer)
        sensorManager.unregisterListener(this, magnetometer)
        sensorManager.unregisterListener(this, pressure)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
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
                Log.i("JOO", "${degree}")
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
            Log.i("TAG2", max.toString())
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

    fun lowPass(input: FloatArray, output: FloatArray) {
        val alpha = 0.05f

        for (i in input.indices) {
            output[i] = output[i] + alpha * (input[i] - output[i])
        }
    }
}