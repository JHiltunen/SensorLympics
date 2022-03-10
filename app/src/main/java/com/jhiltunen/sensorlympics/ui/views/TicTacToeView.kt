package com.jhiltunen.sensorlympics.ui.views

import android.util.Log
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
    val win = ticTacToeViewModel.win.observeAsState()
    val xyCoordinates by ticTacToeViewModel.xyCoordinates.observeAsState()

    SensorLympicsTheme {
        Surface(color = MaterialTheme.colors.background) {
            Card {
                CardStyle {
                    SpaceBetweenColumn {

                        if (win.value?.isNotEmpty() == true) {
                            //ticTacToeViewModel.stopGame(win.toString())
                            Text("GAME END")
                        } else {
                            if (gameIsOn.value == true) {
                                Button(onClick = { ticTacToeViewModel.stopGame("") }) {
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
                                                            // Game ends on draw or win
                                                            if (checkWin(xyCoordinates!!)) {
                                                                Log.d("TICTAC", "WIN!!")
                                                                ticTacToeViewModel.stopGame(turn.value)
                                                                //ticTacToeViewModel.sendInfoToSocket(turn.value, turn.value)
                                                            }
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
                        }

                        TicTacToeRules()
                    }
                }
            }
        }
    }
}

private fun checkWin(xyCoordinates: Array<Array<String>>): Boolean {
    var xLettersInHorizontal = 0
    var xLettersInVertical = 0
    var oLettersInHorizontal = 0
    var oLettersInVertical = 0

    // Vertical rows
    for (column in 0..2) {
        for (row in 0..2) {
            // checking horizontal rows
            if (xyCoordinates[row][column] == "X") {
                xLettersInHorizontal++
            }
            if (xyCoordinates[row][column] == "O") {
                oLettersInHorizontal++
            }

            // checking vertical rows
            if (xyCoordinates[column][row] == "X") {
                xLettersInVertical++
            }
            if (xyCoordinates[column][row] == "O") {
                oLettersInVertical++
            }
        }
        if (xLettersInHorizontal == 3 || oLettersInHorizontal == 3 || xLettersInVertical == 3 || oLettersInVertical == 3) {
            return true
        } else {
            xLettersInHorizontal = 0
            xLettersInVertical = 0
            oLettersInHorizontal = 0
            oLettersInVertical = 0
        }
    }

    // at the end, check diagonal rows
    return checkDiagonalWin(xyCoordinates)
}

private fun checkDiagonalWin(xyCoordinates: Array<Array<String>>): Boolean {
    var xLettersOnDiagonal = 0
    var oLettersOnDiagonal = 0

    // diagonal starting from the left edge of the table
    for (row in 0..2) {
        for (column in 0..2) {
            if (row == column) {
                if (xyCoordinates[row][column] == "X") {
                    xLettersOnDiagonal++
                }
                if (xyCoordinates[row][column] == "O") {
                    oLettersOnDiagonal++
                }
            }
        }
    }
    if (xLettersOnDiagonal == 3 || oLettersOnDiagonal == 3) {
        return true
    }
    xLettersOnDiagonal = 0
    oLettersOnDiagonal = 0

    // diagonal starting from the right edge of the table
    for (row in 0..2) {
        for (column in 3 - 1 downTo 0) {
            if (3 - 1 - row == column) {
                if (xyCoordinates[row][column] == "X") {
                    xLettersOnDiagonal++
                }
                if (xyCoordinates[row][column] == "O") {
                    oLettersOnDiagonal++
                }
            }
        }
    }
    return xLettersOnDiagonal == 3 || oLettersOnDiagonal == 3
}