package com.jhiltunen.sensorlympics.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jhiltunen.sensorlympics.utils.SocketHandler

class TicTacToeViewModel {
    val gson: Gson = Gson()
    private var socketHandler: SocketHandler

    private var _turn: MutableLiveData<String> = MutableLiveData("X")
    val turn: LiveData<String> = _turn
    private var _xyCoordinates: MutableLiveData<Array<Array<String>>> = MutableLiveData(arrayOf(arrayOf(" ", " ", " "), arrayOf(" ", " ", " "), arrayOf(" ", " ", " ")))
    var xyCoordinates: LiveData<Array<Array<String>>> = _xyCoordinates
    //private var xyCoordinates = arrayOf(arrayOf(" ", " ", " "), arrayOf(" ", " ", " "), arrayOf(" ", " ", " "))
    private var _gameIsOn: MutableLiveData<Boolean> = MutableLiveData(false)
    var gameIsOn: LiveData<Boolean> = _gameIsOn
    private var _win: MutableLiveData<String> = MutableLiveData("")
    var win: LiveData<String> = _win

    init {
        Log.d("TICTAC", xyCoordinates::class.java.typeName)
        socketHandler = SocketHandler(ticTacToeViewModel = this)
        socketHandler.setSocket()
        socketHandler.establishConnection()
        socketHandler.onCounter()
    }

    /*fun situationInCoordinates(x: Int, y: Int): String? {
        Log.d("SITUATION", xyCoordinates.value?.get(x)?.get(y).toString())
        return xyCoordinates.value?.get(x)?.get(y)
    }*/

    fun situationInCoordinates(x: Int, y: Int): LiveData<String> {
        val data: MutableLiveData<String> = MutableLiveData("")
        data.postValue(xyCoordinates.value?.get(x)?.get(y).toString())
        return data
    }

    fun addValue(x: Int, y: Int) {
        var nextTurn: String = "X"
        val newXyCoordinates: Array<Array<String>>? = xyCoordinates.value

        if (xyCoordinates.value?.get(x)?.get(y) == " ") {
            newXyCoordinates?.get(x)?.set(y, turn.value.toString())
            Log.d("COORDS", newXyCoordinates?.get(x)?.get(y).toString())
            _xyCoordinates.postValue(newXyCoordinates)
            //xyCoordinates[x][y] = turn.value.toString()
        } else {
            Log.d("COORDS", "return")
            return
        }

        if (turn.value == "X") {
            nextTurn = "O"
            //_turn.postValue("O")
        } else {
            nextTurn = "X"
            //_turn.postValue("X")
        }
        _turn.postValue(nextTurn)
        sendInfoToSocket(nextTurn, "")
    }

    fun stopGame(win: String) {
        _gameIsOn.postValue(false)
        _xyCoordinates.postValue(arrayOf(arrayOf(" ", " ", " "), arrayOf(" ", " ", " "), arrayOf(" ", " ", " ")))
        sendInfoToSocket(turn.value.toString(), win)
        socketHandler.closeConnection()
    }

    fun startGame() {
        _gameIsOn.postValue(true)
    }

    fun sendInfoToSocket(nextTurn: String, win: String) {
        val content = gson.toJson(xyCoordinates.value) //xyCoordinates.map { listOf(*it) }
        val roomName = "room1"
        val sendData = SendMessage(content, nextTurn, roomName, gameIsOn.value!!, win)
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
}
data class SendMessage(val content: String, val nextTurn: String, val roomName: String, val gameIsOn: Boolean, val win: String) {
    override fun toString(): String {
        return "content"
    }
}
