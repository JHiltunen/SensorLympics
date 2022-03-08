package com.jhiltunen.sensorlympics.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.SpaceBetweenColumn
import com.jhiltunen.sensorlympics.pressuregame.PressureViewModelProgress
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme
import kotlin.math.round


@ExperimentalFoundationApi
@Composable
fun PressureApp() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))

    SensorLympicsTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                Surface(color = MaterialTheme.colors.background) {
                    Card {
                        CardStyle {
                            SpaceBetweenColumn {
                                PressurePointer(MainActivity.pressureViewModelProgress)
                            }
                        }
                    }
                }
            }
        )
    }
}

var begin = System.nanoTime()
var end = System.nanoTime()

@ExperimentalFoundationApi
@Composable
fun PressurePointer(pressureViewModelProgress: PressureViewModelProgress) {
    val highScore by pressureViewModelProgress.highScore.observeAsState(0.0)
    val value by pressureViewModelProgress.value.observeAsState(0.0F)
    var valueMax by remember { mutableStateOf(0.0F) }
    var valueMin by remember { mutableStateOf(0.0F) }

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

    val difference = (valueMax.minus(valueMin).times(1000).let { round(it) }).toInt() + 1

    var winOrLose by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(true) }

    var score: Double

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        if (MainActivity.pressureSensorExists) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    if (!winOrLose && gameOver) {
                        valueMax = value
                        valueMin = value

                        winOrLose = true
                        begin = System.nanoTime()
                        gameOver = false
                    } else {
                        winOrLose = false
                        gameOver = true
                    }

                }
            ) {
                if (!winOrLose && gameOver) {
                    Text(stringResource(R.string.pressure_press))
                } else if (winOrLose && gameOver) {
                    Text(stringResource(R.string.pressure_again))
                } else {
                    Text(stringResource(R.string.pressure_quit))
                }
            }

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
                } else if (difference > 180) {
                    val timeDifference = (end.minus(begin)).div(1000000000) + 0.3
                    score = (100 / timeDifference)

                    Text(
                        "\uD83D\uDCA5",
                        fontSize = 180.sp
                    )
                    Text(stringResource(R.string.pressure_good))
                    Text(
                        stringResource(
                            R.string.pressure_time,
                            timeDifference
                        )
                    )
                    Text(
                        stringResource(
                            R.string.pressure_score,
                            score
                        )
                    )

                    if (score == highScore) {
                        Text(stringResource(R.string.new_high_score))
                    }

                    pressureViewModelProgress.upDateScore(score)

                    gameOver = true
                }
            }

        } else {
            Text(text = stringResource(R.string.buy_new_phone))
        }

        Text(stringResource(R.string.pressure_high, highScore))
        PressureRules()
    }
}