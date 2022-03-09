package com.jhiltunen.sensorlympics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.google.gson.Gson
import com.jhiltunen.sensorlympics.utils.SocketHandler
import com.jhiltunen.sensorlympics.utils.SocketHandler.mSocket
import com.jhiltunen.sensorlympics.viewmodels.TicTacToeViewModel
import kotlinx.coroutines.launch

class TicTacToeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}

@Composable
fun SocketView(ticTacToe: TicTacToeViewModel) {
    var text by remember { mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()

    Column() {
        Text(text = text)
        Button(onClick = {
            val content = "data on room1"
            val roomName = "room1"
            val sendData = SendMessage(content, roomName)
            val jsonData = ticTacToe.gson.toJson(sendData)
            println(jsonData)
            mSocket.emit("create", ticTacToe.gson.toJson(roomName))
            ticTacToe.sendMessage()
            //mSocket.emit("newMessage", jsonData)
            //mSocket.emit("counter") }) {

        }) {
            Text("Connect socket")
        }
    }

    mSocket.on("counter") { args ->
        if (args[0] != null) {
            val counter = args[0] as Int
            coroutineScope.launch {
                text = counter.toString()
            }
        }
    }
}

data class SendMessage(val content: String, val roomName: String) {}