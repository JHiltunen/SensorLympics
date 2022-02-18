package com.jhiltunen.sensorlympics.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TicTacToeViewModel() {
    private var _turn: MutableLiveData<String> = MutableLiveData("X")
    val turn: LiveData<String> = _turn
    private var xyCoordinates =
        arrayOf(arrayOf(" ", " ", " "), arrayOf(" ", " ", " "), arrayOf(" ", " ", " "))
    private var _gameIsOn: MutableLiveData<Boolean> = MutableLiveData(true)
    var gameIsOn: LiveData<Boolean> = _gameIsOn

    fun situationInCoordinates(x: Int, y: Int): String {
        return xyCoordinates[x][y]
    }

    fun addValue(x: Int, y: Int) {
        if (xyCoordinates[x][y] == " ") {
            xyCoordinates[x][y] = turn.value.toString()
        } else {
            return
        }

        if (turn.value == "X") _turn.postValue("O") else { _turn.postValue("X") }
    }

    fun stopGame() {
        _gameIsOn.postValue(false)
    }

    fun checkWin(): Boolean {
        var xLettersInHorizontal = 0
        var xLettersInVertical = 0
        var oLettersInHorizontal = 0
        var oLettersInVertical = 0

        // Vertical rows
        for (column in 0..2) {
            for (row in 0..2) {
                // checking horizontal rows
                if (situationInCoordinates(row, column) == "X") {
                    xLettersInHorizontal++
                }
                if (situationInCoordinates(row, column) == "O") {
                    oLettersInHorizontal++
                }

                // checking vertical rows
                if (situationInCoordinates(column, row) == "X") {
                    xLettersInVertical++
                }
                if (situationInCoordinates(column, row) == "O") {
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
        return checkDiagonalWin()
    }

    private fun checkDiagonalWin(): Boolean {
        var xLettersOnDiagonal = 0
        var oLettersOnDiagonal = 0

        // diagonal starting from the left edge of the table
        for (row in 0..2) {
            for (column in 0..2) {
                if (row == column) {
                    if (situationInCoordinates(row, column) == "X") {
                        xLettersOnDiagonal++
                    }
                    if (situationInCoordinates(row, column) == "O") {
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
                    if (situationInCoordinates(row, column) == "X") {
                        xLettersOnDiagonal++
                    }
                    if (situationInCoordinates(row, column) == "O") {
                        oLettersOnDiagonal++
                    }
                }
            }
        }
        return xLettersOnDiagonal == 3 || oLettersOnDiagonal == 3
    }
}