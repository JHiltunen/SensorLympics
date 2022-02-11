package com.jhiltunen.sensorlympics.magnetgame

import SensorViewModel
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.random.Random

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
                color = Taffy,
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

