package com.jhiltunen.sensorlympics.utils


import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jhiltunen.sensorlympics.viewmodels.TicTacToeViewModel
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException


class SocketHandler(val ticTacToeViewModel: TicTacToeViewModel) {
    val gson: Gson = Gson()
    lateinit var mSocket: Socket

    init {
        setSocket()
    }

    @Synchronized
    fun setSocket() {
        try {
// "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
// "http://localhost:3000/" will not work
// If you want to use your physical phone you could use the your ip address plus :3000
// This will allow your Android Emulator and physical device at your home to connect to the server
            //mSocket = IO.socket("http://10.0.2.2:3000")
            mSocket = IO.socket("https://sensorlympics-backend.herokuapp.com/")
        } catch (e: URISyntaxException) {

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun getAllRooms() {

    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }

    @Synchronized
    fun onCounter() {
        mSocket.on("counter") { args ->
            if (args != null) {
                val propertiesJson: JSONObject = args[0] as JSONObject
                val content = propertiesJson.get("content")
                val nextTurn = propertiesJson.get("nextTurn")
                val roomName = propertiesJson.get("roomName")
                val gameIsOn = propertiesJson.get("gameIsOn")


                Log.d("SOCKET", content.toString())
                Log.d("SOCKET", nextTurn.toString())
                //Log.d("SOCKET", roomName.toString())

                val gson = Gson()

                val gsonPretty = GsonBuilder().setPrettyPrinting().create()
                val recordsSerializedPretty = gsonPretty.toJson(content)
                println(recordsSerializedPretty)
                val json: Array<Array<String>> = gson.fromJson(content.toString(), Array<Array<String>>::class.java)

                ticTacToeViewModel.setData(json, nextTurn.toString(), gameIsOn.toString())
            }
        }
    }
}