package com.jhiltunen.sensorlympics.pressuregame

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.ui.theme.Purple500
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme


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

    Card(modifier = Modifier
        .padding(12.dp)
        .fillMaxHeight(fraction = 0.5f)
        .fillMaxWidth()
        .fillMaxHeight(fraction = 0.5f)
        ,
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {

            Text(stringResource(com.jhiltunen.sensorlympics.R.string.currentMaxMin))

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



