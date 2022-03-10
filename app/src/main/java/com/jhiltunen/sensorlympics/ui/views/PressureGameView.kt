package com.jhiltunen.sensorlympics.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhiltunen.sensorlympics.ui.layouts.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.ui.layouts.SpaceBetweenColumn
import com.jhiltunen.sensorlympics.pressuregame.PressureViewModelProgress
import com.jhiltunen.sensorlympics.room.Score
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme
import com.jhiltunen.sensorlympics.viewmodels.ScoreViewModel
import kotlin.math.round
import kotlin.random.Random


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
                                PressurePointer(MainActivity.pressureViewModelProgress, MainActivity.scoreViewModel)
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
fun PressurePointer(pressureViewModelProgress: PressureViewModelProgress, scoreViewModel: ScoreViewModel) {
    val highScore by scoreViewModel.getHighscore("Pressure").observeAsState()
    val value by pressureViewModelProgress.value.observeAsState(0.0F)
    var valueMax by remember { mutableStateOf(0.0F) }
    var valueMin by remember { mutableStateOf(0.0F) }
    var pressureDifference by remember { mutableStateOf(180)}

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
    var scoreChecker by remember { mutableStateOf(true) }

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

                        pressureDifference = Random.nextInt(180, 300)
                        scoreChecker = true
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
                Image(
                    painter = painterResource(id = R.drawable.compass_1_2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            } else {
                if (difference < pressureDifference) {
                    Text(
                        "\uD83C\uDF88",
                        fontSize = difference.sp
                    )
                    end = System.nanoTime()
                } else if (difference > pressureDifference) {
                    val timeDifference = (end.minus(begin)).div(1000000000) + 0.3
                    score = (pressureDifference / timeDifference)

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
                            score.toInt()
                        )
                    )

                    pressureViewModelProgress.upDateScore(score)
                    if (scoreChecker) {
                        scoreViewModel.insert(Score(0,"Pressure", score.toLong()))
                        scoreChecker = false
                    }

                    gameOver = true
                }
            }
            Text(stringResource(R.string.pressure_high, highScore ?: 0))
        } else {
            Text(text = stringResource(R.string.buy_new_phone))
        }
        PressureRules()
    }
}