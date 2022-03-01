package com.jhiltunen.sensorlympics.magnetgame

import android.app.Application
import android.util.Log
import com.jhiltunen.sensorlympics.FILENAMEMAGNET
import com.jhiltunen.sensorlympics.MainActivity
import java.io.FileNotFoundException
import kotlin.math.abs
import kotlin.random.Random


fun northOrBust(direction: Int) {
    val score: Float
    val angle = MainActivity.magnetViewModel.degree.value
    //FOR DEBUGGING:
    //val jorma = 2
    when (direction) {
        //North
        1 -> if(MainActivity.magnetViewModel.degree.value!! > 334 || MainActivity.magnetViewModel.degree.value!! < 27) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1/(abs(angle!!.minus(334))))
            Log.i("DIR", "Vau! $score")
            MainActivity.magnetViewModel.upDateScore(score)

        } else MainActivity.magnetViewModel.upDateWin(1)
        //East
        2 -> if(MainActivity.magnetViewModel.degree.value!! > 64 && MainActivity.magnetViewModel.degree.value!! < 116) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1/(abs(angle!!.minus(64))))
            Log.i("DIR", "Vau!  $score")
            MainActivity.magnetViewModel.upDateScore(score)

        } else MainActivity.magnetViewModel.upDateWin(1)
        //South
        3 -> if(MainActivity.magnetViewModel.degree.value!! > 154 && MainActivity.magnetViewModel.degree.value!! < 206) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1/(abs(angle!!.minus(154))))
            Log.i("DIR", "Vau!  $score")
            MainActivity.magnetViewModel.upDateScore(score)

        } else MainActivity.magnetViewModel.upDateWin(1)
        //West
        4 -> if(MainActivity.magnetViewModel.degree.value!! > 244 && MainActivity.magnetViewModel.degree.value!! < 296) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1/(abs(angle!!.minus(244))))
            Log.i("DIR", "Vau!  $score")
            MainActivity.magnetViewModel.upDateScore(score)

        } else MainActivity.magnetViewModel.upDateWin(1)
        else -> MainActivity.magnetViewModel.upDateWin(1)
    }
}

fun chooseDirection() {
    val chosen =  Random.nextInt(1, 5)
    MainActivity.magnetViewModel.upDateChosen(chosen)
    Log.i("DIR", "Chosen: $chosen")
}

fun readFile(app: Application): List<String> =
    //if (fileIs(app)) {
    try {
        app.openFileInput(FILENAMEMAGNET)?.bufferedReader().use {
            it?.readLines() ?: emptyList()
        }
    } catch (e : FileNotFoundException) {
        emptyList()
    }