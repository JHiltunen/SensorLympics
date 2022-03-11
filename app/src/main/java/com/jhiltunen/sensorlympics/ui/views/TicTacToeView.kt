package com.jhiltunen.sensorlympics.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.SignalWifiConnectedNoInternet4
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.room.Score
import com.jhiltunen.sensorlympics.ui.layouts.CardStyle
import com.jhiltunen.sensorlympics.ui.layouts.SpaceBetweenColumn
import com.jhiltunen.sensorlympics.ui.theme.MaxRed
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme
import com.jhiltunen.sensorlympics.utils.isOnline
import com.jhiltunen.sensorlympics.viewmodels.ScoreViewModel
import com.jhiltunen.sensorlympics.viewmodels.TicTacToeViewModel

@ExperimentalFoundationApi
@Composable
fun TicTacToeView(ticTacToeViewModel: TicTacToeViewModel, scoreViewModel: ScoreViewModel) {
    val turn = ticTacToeViewModel.turn.observeAsState("X")
    val gameIsOn = ticTacToeViewModel.gameIsOn.observeAsState()
    val win = ticTacToeViewModel.win.observeAsState()
    val xyCoordinates by ticTacToeViewModel.xyCoordinates.observeAsState()
    val airplane: Boolean? by MainActivity.receiverViewModel.airplane.observeAsState()

    SensorLympicsTheme {
        Surface(color = MaterialTheme.colors.background) {
            CardStyle {
                SpaceBetweenColumn {
                    if (!airplane!! && isOnline(LocalContext.current)) {
                        if (win.value!!.isNotEmpty() && gameIsOn.value == false) {
                            Text(stringResource(id = R.string.tictactoe_win, win.value.toString()))
                            scoreViewModel.insert(
                                Score(
                                    0,
                                    "TicTac",
                                    0
                                )
                            )
                        }

                        if (gameIsOn.value == true) {

                            Button(onClick = {
                                ticTacToeViewModel.stopGame()
                            }) {
                                Text(
                                    text = stringResource(id = R.string.pressure_quit)
                                )
                            }

                            Text(text = stringResource(id = R.string.turn, turn.value))

                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                for (row in 0..2) {
                                    Column(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .height(300.dp),
                                        verticalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        for (column in 0..2) {
                                            Button(
                                                modifier = Modifier
                                                    .padding(15.dp)
                                                    .size(40.dp), onClick = {
                                                    if (gameIsOn.value == true) {
                                                        ticTacToeViewModel.addValue(column, row)
                                                    }
                                                }, enabled = gameIsOn.value == true
                                            ) {
                                                Text(text = xyCoordinates?.get(column)?.get(row)!!)
                                            }
                                        }
                                    }

                                }
                            }
                        } else {
                            Button(onClick = { ticTacToeViewModel.startGame() }) {
                                Text(
                                    text = stringResource(id = R.string.pressure_press)
                                )
                            }
                        }
                    } else {

                        Card(
                            modifier = Modifier.padding(16.dp),
                            elevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    stringResource(R.string.tictactoe_not),
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp),

                                    ) {
                                    Spacer(modifier = Modifier.padding(16.dp))
                                    Icon(
                                        Icons.TwoTone.SignalWifiConnectedNoInternet4,
                                        "",
                                        Modifier.size(80.dp),
                                        tint = MaxRed
                                    )
                                }
                            }
                        }
                    }

                    TicTacToeRules()
                }
            }
        }
    }
}