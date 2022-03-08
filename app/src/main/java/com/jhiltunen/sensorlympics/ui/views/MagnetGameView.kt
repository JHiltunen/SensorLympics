package com.jhiltunen.sensorlympics.ui.views

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.MainActivity.Companion.magnetViewModel
import com.jhiltunen.sensorlympics.MainActivity.Companion.scoreViewModel
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.SpaceBetweenColumn
import com.jhiltunen.sensorlympics.viewmodels.MagnetViewModel
import com.jhiltunen.sensorlympics.magnetgame.chooseDirection
import com.jhiltunen.sensorlympics.magnetgame.northOrBust
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme


@ExperimentalFoundationApi
@Composable
fun SensorMagnetApp(context: Context) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var winOrLose by remember { mutableStateOf(false) }
    var direction by remember { mutableStateOf("") }
    //val highScore by magnetViewModel.highScore.observeAsState()
    val highScore by scoreViewModel.getHighscore("Magneto").observeAsState()

    SensorLympicsTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                Surface(color = MaterialTheme.colors.background) {
                    if (MainActivity.magnetometerSensorExists) {
                        Card {
                            CardStyle {
                                SpaceBetweenColumn {
                                    Button(
                                        onClick = {
                                            if (!winOrLose) {
                                                winOrLose = true
                                                val theChosen =
                                                    magnetViewModel.chosen.value ?: 1
                                                when (theChosen) {
                                                    1 -> direction =
                                                        context.getString(R.string.north)
                                                    2 -> direction =
                                                        context.getString(R.string.east)
                                                    3 -> direction =
                                                        context.getString(R.string.south)
                                                    4 -> direction =
                                                        context.getString(R.string.west)
                                                }
                                                Log.i("DIR", "Onclick chosen: $theChosen")
                                                northOrBust(theChosen)

                                            } else {
                                                winOrLose = false
                                                chooseDirection()
                                                magnetViewModel.upDateWin(0)
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        if (!winOrLose) {
                                            when (magnetViewModel.chosen.value ?: 1) {
                                                1 -> direction = context.getString(R.string.north)
                                                2 -> direction = context.getString(R.string.east)
                                                3 -> direction = context.getString(R.string.south)
                                                4 -> direction = context.getString(R.string.west)
                                            }
                                            Text(stringResource(R.string.btn_start, direction))
                                        } else {
                                            Text(stringResource(R.string.btn_again))
                                        }
                                    }
                                    Spacer(Modifier.height(15.dp))
                                    ShowWinOrLose(magnetViewModel)
                                    CompassPointer(magnetViewModel)
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(stringResource(R.string.pressure_high, highScore?.toInt()
                                            ?: 0))
                                        MagnetoRules()
                                    }
                                }
                            }
                        }
                    } else {
                        Text(text = stringResource(R.string.buy_new_phone))
                    }
                }
            }
        )
    }
}

@Composable
fun ShowWinOrLose(magnetViewModel: MagnetViewModel) {
    val winOrLoseOr by magnetViewModel.win.observeAsState()
    val chosen by magnetViewModel.chosen.observeAsState()
    val score by magnetViewModel.score.observeAsState()
    val direction: String = when (chosen) {
        1 -> stringResource(R.string.north)
        2 -> stringResource(R.string.east)
        3 -> stringResource(R.string.south)
        4 -> stringResource(R.string.west)
        else -> ""
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        when (winOrLoseOr) {
            0 -> Text(stringResource(R.string.result_not_yet), Modifier.padding(8.dp))
            1 -> Text(stringResource(R.string.result_bad), Modifier.padding(8.dp))
            2 -> Text(
                stringResource(R.string.result_good, direction, score?.toInt() ?: 0),
                Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
    }
}

@Composable
fun CompassPointer(magnetViewModel: MagnetViewModel) {
    val degree by magnetViewModel.degree.observeAsState()
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.5f),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Spacer(modifier = Modifier.height(7.dp))
            Image(
                painter = painterResource(id = R.drawable.compass_1_2),
                contentDescription = null,
                modifier = Modifier
                    .rotate(degree ?: 0f)
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(7.dp))
        }
    }
}