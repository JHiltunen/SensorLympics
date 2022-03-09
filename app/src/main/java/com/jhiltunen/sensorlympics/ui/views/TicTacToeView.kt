package com.jhiltunen.sensorlympics.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.SpaceBetweenColumn
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme
import com.jhiltunen.sensorlympics.viewmodels.TicTacToeViewModel

@Composable
fun TicTacToeView(ticTacToeViewModel: TicTacToeViewModel) {
    val turn = ticTacToeViewModel.turn.observeAsState("X")
    val gameIsOn = ticTacToeViewModel.gameIsOn.observeAsState()

    SensorLympicsTheme {
        Surface(color = MaterialTheme.colors.background) {
            Card {
                CardStyle {
                    SpaceBetweenColumn {

                        if (gameIsOn.value == true) {
                            Button(onClick = { ticTacToeViewModel.stopGame() }) {
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
                                            var btnText by remember { mutableStateOf("") }

                                            Button(
                                                modifier = Modifier
                                                    .padding(15.dp)
                                                    .size(40.dp), onClick = {
                                                    if (gameIsOn.value == true) {
                                                        ticTacToeViewModel.addValue(column, row)
                                                        btnText =
                                                            ticTacToeViewModel.situationInCoordinates(
                                                                column,
                                                                row
                                                            )

                                                        // Game ends on draw or win
                                                        if (ticTacToeViewModel.checkWin()) {
                                                            ticTacToeViewModel.stopGame()
                                                        }
                                                    }
                                                }, enabled = gameIsOn.value == true
                                            ) {
                                                Text(btnText)
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

                        TicTacToeRules()
                    }
                }
            }
        }
    }
}