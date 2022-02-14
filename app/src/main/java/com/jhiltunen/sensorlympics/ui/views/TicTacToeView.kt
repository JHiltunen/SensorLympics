package com.jhiltunen.sensorlympics.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.jhiltunen.sensorlympics.utils.TicTacToe

@Composable
// Game 3
fun TicTacToeView(ticTacToe: TicTacToe) {
    var turn = ticTacToe.turn.observeAsState()
    var gameIsOn = ticTacToe.gameIsOn.observeAsState()
    Column() {
        Text(text = "Turn ${turn.value}")
        for (row in 0..2) {
            Row() {
                for (column in 0..2) {
                    var btnText by remember { mutableStateOf("") }

                    Button(onClick = {
                        if (gameIsOn.value == true) {
                            ticTacToe.addValue(column, row)
                            btnText = ticTacToe.situationInCoordinates(column, row)

                            // Game ends on draw or win
                            if (ticTacToe.checkWin()) {
                                ticTacToe.stopGame()
                            }
                        }
                    }, enabled = gameIsOn.value == true) {
                        Text(btnText)
                    }
                }
            }

        }
    }
}