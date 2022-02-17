package com.jhiltunen.sensorlympics.pressuregame

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
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
                    Card( modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(15.dp),
                        elevation = 10.dp) {
                        Column(modifier = Modifier.padding(15.dp) ) {
                            ShowPressureData(MainActivity.pressureViewModel)
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

@Composable
fun FeaturedCircularProgressIndicator(pressureViewModelProgress: PressureViewModelProgress) {
    val value by pressureViewModelProgress.value.observeAsState(null)
    val valueMax by pressureViewModelProgress.valueMax.observeAsState(null)
    val valueMin by pressureViewModelProgress.valueMin.observeAsState(null)

    val value3 = value?.minus(valueMin!!)?.div((valueMax?.minus(valueMin!!)!!))

    val difference = (valueMax?.minus(valueMin!!)?.times(1000)?.let { round(it) })?.toInt()

    val gameValue = (value?.minus(900)?.let { round(it) })?.toInt()

    var winOrLose by remember { mutableStateOf(false) }

    Log.i("PRESSURE", "?: $gameValue")
    Log.i("PRESSURE", "??: $difference")

    Card(modifier = Modifier
        .padding(12.dp)
        .fillMaxHeight(fraction = 0.95f)
        .fillMaxWidth()
        .fillMaxHeight(fraction = 1.75f)
        ,
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button (
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    winOrLose = !winOrLose
                }
            ) {
                if(!winOrLose) {
                    Text("Paina perkele paina!")
                } else {
                    Text("Älä saatana paina!")
                }
            }
            Text(stringResource(com.jhiltunen.sensorlympics.R.string.currentMaxMin))
            if (difference != null) {
                if (difference < 180) {
                    Text(
                        "\uD83C\uDF88",
                        fontSize = difference.sp
                    )
                } else {
                    Text(
                        "\uD83D\uDCA5",
                        fontSize = 180.sp
                    )
                }
            } else {
                Text(
                    "\uD83C\uDF88",
                    fontSize = 60.sp
                )
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



