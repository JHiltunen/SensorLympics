package com.jhiltunen.sensorlympics.pressuregame

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.ui.theme.Purple500
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme
import kotlin.math.round


@Composable
fun PressureApp() {

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))


    SensorLympicsTheme() {
        Scaffold(
            scaffoldState = scaffoldState,

            content = {
                Surface(color = MaterialTheme.colors.background) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(15.dp),
                        elevation = 10.dp
                    ) {
                        Column(modifier = Modifier.padding(15.dp)) {
                            //ShowPressureData(MainActivity.pressureViewModel)
                            FeaturedCircularProgressIndicator(MainActivity.pressureViewModelProgress)
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ShowPressureData(pressureViewModel: PressureViewModel) {
    val value by pressureViewModel.value.observeAsState()
    Text(value ?: "", Modifier.padding(18.dp))
}
var begin = System.nanoTime()
var end = System.nanoTime()

@Composable
fun FeaturedCircularProgressIndicator(pressureViewModelProgress: PressureViewModelProgress) {
    val value by pressureViewModelProgress.value.observeAsState(0.0F)
    var valueMax by remember { mutableStateOf(0.0F) }
    var valueMin by remember { mutableStateOf(0.0F) }
    //val valueMax by pressureViewModelProgress.valueMax.observeAsState(null)
    //val valueMin by pressureViewModelProgress.valueMin.observeAsState(null)

    if (valueMax == 0.0F) {
        valueMax = value
        valueMin = value
    }

    if (value > valueMax) {
        valueMax = value
    }

    if (value < valueMin) {
        valueMin = value
    }

    val value3 = value.minus(valueMin).div((valueMax.minus(valueMin)))

    val difference = (valueMax.minus(valueMin).times(1000).let { round(it) }).toInt()

    val gameValue = (value.minus(900).let { round(it) }).toInt()

    var winOrLose by remember { mutableStateOf(false) }




    Log.i("PRESSURE", "?: $gameValue")
    Log.i("PRESSURE", "??: $difference")

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight(fraction = 0.95f)
            .fillMaxWidth()
            .fillMaxHeight(fraction = 1.75f),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    if (!winOrLose) {
                        // MainActivity.pressureViewModelProgress.updateValue(0.0F,0.0F,0.0F)
                        /*
                     value?.let {
                         MainActivity.pressureViewModelProgress.updateValue(
                             it,
                             value!!, value!!
                         )
                     }
                         */
                        valueMax = value
                        valueMin = value

                        Log.i("PRESSURES", "??? $value")
                        winOrLose = true
                        begin = System.nanoTime()
                        Log.i("PRESSURES", "?begin: $begin")
                    } else {
                        winOrLose = false

                    }

                }
            ) {
                if (!winOrLose) {
                    Text("Paina perkele paina!")
                } else {
                    Text("Älä saatana paina!")
                }
            }
            Text("$value")
            Text("$valueMax")
            Text("$valueMin")
            Text("$difference")
            Text(stringResource(com.jhiltunen.sensorlympics.R.string.currentMaxMin))
            if (!winOrLose) {
                Text(
                    "\uD83C\uDF88",
                    fontSize = 60.sp
                )
            } else {
                if (difference < 180) {
                    Text(
                        "\uD83C\uDF88",
                        fontSize = difference.sp
                    )
                    end = System.nanoTime()
                } else if (difference > 180){

                    val timeDifference = end.minus(begin)
                    Log.i("PRESSURES", "?end: $end")
                    Log.i("PRESSURES", "?difference: $timeDifference")

                    Text(
                        "\uD83D\uDCA5",
                        fontSize = 180.sp
                    )
                    Text("Hyvä hyvä!")

                    Text("Elapsed time in milliseconds: ${timeDifference}")
                    //winOrLose = false
                }
            }

            Spacer(modifier = Modifier.height(7.dp))

            LinearProgressIndicator(
                progress = value3 ?: 0.4f,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(fraction = 0.15f),
                color = Purple500,
                backgroundColor = Color.Blue
            )
        }
    }
}
