package com.jhiltunen.sensorlympics.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.jhiltunen.sensorlympics.utils.TicTacToe

@Composable
// Game 3
fun TicTacToeView() {
    val ticTacToe = TicTacToe()

    for (row in 0..2) {
        Column() {
            for (column in 0..2) {
                var x = column
                var y = row
                Row() {
                    Button(onClick = {
                        if (ticTacToe.isOn()) {
                            ticTacToe.addValue(x, y)
                            // game status set text ristinolla vuoro

                            // Game ends on draw or win
                            if (ticTacToe.checkWin()) {
                                ticTacToe.stopGame()
                            }
                        }
                    }) {
                        Text(ticTacToe.situationInCoordinates(x, y))
                    }
                }

            }
        }
    }
}