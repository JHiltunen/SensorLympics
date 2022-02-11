import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhiltunen.sensorlympics.MainActivity.Companion.sensorViewModel
import com.jhiltunen.sensorlympics.R
import androidx.compose.runtime.livedata.observeAsState
import com.jhiltunen.sensorlympics.ui.theme.Purple500
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.random.Random

class SensorViewModel : ViewModel() {
    private val _value: MutableLiveData<String> = MutableLiveData()
    val value: LiveData<String> = _value

    private val _xX: MutableLiveData<Float> = MutableLiveData()
    val xX: LiveData<Float> = _xX

    private val _yY: MutableLiveData<Float> = MutableLiveData()
    val yY: LiveData<Float> = _yY

    private val _degree: MutableLiveData<Float> = MutableLiveData()
    val degree: LiveData<Float> = _degree

    private val _win: MutableLiveData<Int> = MutableLiveData()
    val win: LiveData<Int> = _win

    private val _chosen: MutableLiveData<Int> = MutableLiveData()
    val chosen: LiveData<Int> = _chosen

    fun updateValue(value: String, xValue: Float, yValue: Float) {
        _value.value = value
        _xX.value = xValue
        _yY.value = yValue
    }

    fun upDateDegree(degree: Float) {
        _degree.value = degree
    }

    fun upDateWin(win: Int) {
        _win.value = win
    }
    fun upDateChosen(chosen: Int) {
        _chosen.value = chosen
    }
}

@Composable
fun SensorApp(context: Context) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var winOrLose by remember { mutableStateOf(false) }
    var direction by remember { mutableStateOf("") }
    //val context: Context = context

    SensorLympicsTheme() {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    backgroundColor = MaterialTheme.colors.primary,
                )
            },
            content = {
                Surface(color = MaterialTheme.colors.background) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        elevation = 10.dp
                    ) {
                        Column(modifier = Modifier.padding(15.dp)) {
                            Button(
                                onClick = {
                                    if (!winOrLose) {
                                        winOrLose = true
                                        val theChosen = sensorViewModel.chosen.value ?: 1
                                        when (theChosen) {
                                            1 -> direction =  context.getString(R.string.north)
                                            2 -> direction =  context.getString(R.string.east)
                                            3 -> direction =  context.getString(R.string.south)
                                            4 -> direction =  context.getString(R.string.west)
                                        }
                                        Log.i("DIR", "Onclick chosen: $theChosen")
                                        northOrBust(theChosen)

                                    } else {
                                        winOrLose = false
                                        chooseDirection()
                                        sensorViewModel.upDateWin(0)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (!winOrLose) {
                                    val theChosen = sensorViewModel.chosen.value ?: 1
                                    when (theChosen) {
                                        1 -> direction =  context.getString(R.string.north)
                                        2 -> direction =  context.getString(R.string.east)
                                        3 -> direction =  context.getString(R.string.south)
                                        4 -> direction =  context.getString(R.string.west)
                                    }
                                    Text(stringResource(R.string.btn_start, direction))
                                } else {
                                    Text(stringResource(R.string.btn_again))
                                }
                            }
                            Spacer(Modifier.height(15.dp))
                            ShowWinOrLose(sensorViewModel)
                            //ShowSenorData(MainActivity.sensorViewModel)
                            FeaturedCircularProgressIndicator(sensorViewModel)
                        }
                    }
                }
            }
        )
    }
}



fun northOrBust(direction: Int) {
    when (direction) {
        1 -> if(sensorViewModel.degree.value!! > 334 || sensorViewModel.degree.value!! < 27) {
            sensorViewModel.upDateWin(2)
            Log.i("DIR", "Vau!")
        } else sensorViewModel.upDateWin(1)
        2 -> if(sensorViewModel.degree.value!! > 64 && sensorViewModel.degree.value!! < 116) {
            sensorViewModel.upDateWin(2)
            Log.i("DIR", "Vau!")
        } else sensorViewModel.upDateWin(1)
        3 -> if(sensorViewModel.degree.value!! > 154 && sensorViewModel.degree.value!! < 206) {
            sensorViewModel.upDateWin(2)
            Log.i("DIR", "Vau!")
        } else sensorViewModel.upDateWin(1)
        4 -> if(sensorViewModel.degree.value!! > 244 && sensorViewModel.degree.value!! < 296) {
            sensorViewModel.upDateWin(2)
            Log.i("DIR", "Vau!")
        } else sensorViewModel.upDateWin(1)
        else -> sensorViewModel.upDateWin(1)
    }
}



@Composable
fun ShowWinOrLose(sensorViewModel: SensorViewModel) {
    val winOrLoseOr by sensorViewModel.win.observeAsState()
    val chosen by sensorViewModel.chosen.observeAsState()
    val direction: String
    when (chosen) {
        1 -> direction =  stringResource(R.string.north)
        2 -> direction =  stringResource(R.string.east)
        3 -> direction =  stringResource(R.string.south)
        4 -> direction =  stringResource(R.string.west)
        else  -> direction =  ""
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        when (winOrLoseOr) {
            0 -> Text(stringResource(R.string.result_not_yet), Modifier.padding(8.dp))
            1 -> Text(stringResource(R.string.result_bad), Modifier.padding(8.dp))
            2 -> Text(stringResource(R.string.result_good, direction), Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(7.dp))
    }
}

@Composable
fun FeaturedCircularProgressIndicator(sensorViewModel: SensorViewModel) {
    val degree by sensorViewModel.degree.observeAsState()
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight(fraction = 0.5f)
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.5f),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text((stringResource(R.string.currentMaxMin)))

            Spacer(modifier = Modifier.height(7.dp))

            CircularProgressIndicator(
                progress = degree?.div(360) ?: 0.0f,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.25f),
                color = Purple500,
                strokeWidth = 5.dp
            )
        }
    }
}

fun chooseDirection() {
    val chosen =  Random.nextInt(1, 5)
    sensorViewModel.upDateChosen(chosen)
    Log.i("DIR", "Chosen: $chosen")
}
 /*
@Composable
fun ShowSenorData(sensorViewModel: SensorViewModel) {
    val value by sensorViewModel.value.observeAsState()
    val xX by sensorViewModel.xX.observeAsState()
    val yY by sensorViewModel.yY.observeAsState()
    var angle = yY?.let { xX?.let { it1 -> atan2(it, it1) } }

    var angleThe = (PI / 2)
    Log.i("SENOR", value.toString())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(value ?: "", Modifier.padding(8.dp))
        Spacer(modifier = Modifier.height(7.dp))
        Text(xX.toString() ?: "", Modifier.padding(8.dp))
    }
}
*/



