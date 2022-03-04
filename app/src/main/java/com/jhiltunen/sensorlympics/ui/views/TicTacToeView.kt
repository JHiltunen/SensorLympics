package com.jhiltunen.sensorlympics.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.tictactoe.TicTacToeViewModel

@Composable
// Game 3
fun TicTacToeView(ticTacToeViewModel: TicTacToeViewModel) {
    val turn = ticTacToeViewModel.turn.observeAsState()
    val gameIsOn = ticTacToeViewModel.gameIsOn.observeAsState()
    Text(text = "Turn ${turn.value}")

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        for (row in 0..2) {
            Column(modifier = Modifier.padding(10.dp).height(300.dp), verticalArrangement = Arrangement.SpaceEvenly) {
                for (column in 0..2) {
                    var btnText by remember { mutableStateOf("") }

                    Button(modifier = Modifier.padding(15.dp).size(50.dp), onClick = {
                        if (gameIsOn.value == true) {
                            ticTacToeViewModel.addValue(column, row)
                            btnText = ticTacToeViewModel.situationInCoordinates(column, row)

                            // Game ends on draw or win
                            if (ticTacToeViewModel.checkWin()) {
                                ticTacToeViewModel.stopGame()
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