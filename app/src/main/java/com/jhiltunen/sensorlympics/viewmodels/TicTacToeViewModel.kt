package com.jhiltunen.sensorlympics.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jhiltunen.sensorlympics.utils.SocketHandler

class TicTacToeViewModel {
    private val gson: Gson = Gson()
    private var socketHandler: SocketHandler = SocketHandler(ticTacToeViewModel = this)

    private var _turn: MutableLiveData<String> = MutableLiveData("X")
    val turn: LiveData<String> = _turn
    private var _xyCoordinates: MutableLiveData<Array<Array<String>>> = MutableLiveData(arrayOf(arrayOf(" ", " ", " "), arrayOf(" ", " ", " "), arrayOf(" ", " ", " ")))
    var xyCoordinates: LiveData<Array<Array<String>>> = _xyCoordinates
    private var _gameIsOn: MutableLiveData<Boolean> = MutableLiveData(false)
    var gameIsOn: LiveData<Boolean> = _gameIsOn
    private var _win: MutableLiveData<String> = MutableLiveData("")
    var win: LiveData<String> = _win

    init {
        socketHandler.setSocket()
        socketHandler.establishConnection()
        socketHandler.onCounter()
    }

    fun addValue(x: Int, y: Int) {
        var nextTurn: String
        val newXyCoordinates: Array<Array<String>>? = xyCoordinates.value

        if (xyCoordinates.value?.get(x)?.get(y) == " ") {
            newXyCoordinates?.get(x)?.set(y, turn.value.toString())
            _xyCoordinates.postValue(newXyCoordinates)
        } else {
            return
        }

        if (turn.value == "X") {
            nextTurn = "O"
        } else {
            nextTurn = "X"
        }
        _turn.postValue(nextTurn)
        if (checkWin(xyCoordinates.value!!)) {
            _gameIsOn.postValue(false)
            sendInfoToSocket(xyCoordinates.value!!, nextTurn, false,"#")
        } else {
            sendInfoToSocket(xyCoordinates.value!!, nextTurn, true,"")
        }
    }

    fun stopGame() {
        _gameIsOn.postValue(false)
        val newStringArray = arrayOf(arrayOf(" ", " ", " "), arrayOf(" ", " ", " "), arrayOf(" ", " ", " "))
        _xyCoordinates.postValue(newStringArray)

        _turn.postValue("X")

        sendInfoToSocket(newStringArray, "X", false,"")
    }

    fun startGame() {
        val newStringArray = arrayOf(arrayOf(" ", " ", " "), arrayOf(" ", " ", " "), arrayOf(" ", " ", " "))
        _xyCoordinates.postValue(newStringArray)
        _gameIsOn.postValue(true)
        _turn.postValue("X")

        sendInfoToSocket(newStringArray, "X", true,"")
    }

    private fun sendInfoToSocket(xyCoordinates: Array<Array<String>>, nextTurn: String, gameIsOn: Boolean, win: String) {
        val content = gson.toJson(xyCoordinates)
        val sendData = SendMessage(content, nextTurn, gameIsOn, win)
        val jsonData = gson.toJson(sendData)
        socketHandler.mSocket.emit("create", jsonData)
    }

    fun setData(newXyCoordinates: Array<Array<String>>, nextTurn: String, gameIsOn: Boolean, win: String) {
        _xyCoordinates.postValue(newXyCoordinates)
        Log.d("COORD", "OLD: $xyCoordinates -> new: $newXyCoordinates")
        _turn.postValue(nextTurn)
        _gameIsOn.postValue(gameIsOn)
        _win.postValue(win)
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
}
data class SendMessage(val content: String, val nextTurn: String, val gameIsOn: Boolean, val win: String) {
    override fun toString(): String {
        return "content"
    }
}
