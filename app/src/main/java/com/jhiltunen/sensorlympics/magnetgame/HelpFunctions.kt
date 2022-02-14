package com.jhiltunen.sensorlympics.magnetgame

import android.util.Log
import com.jhiltunen.sensorlympics.MainActivity
import kotlin.math.abs
import kotlin.random.Random


fun northOrBust(direction: Int) {
    var score = 0f
    val angle = MainActivity.sensorViewModel.degree.value
    when (direction) {
        1 -> if(MainActivity.sensorViewModel.degree.value!! > 334 || MainActivity.sensorViewModel.degree.value!! < 27) {
            MainActivity.sensorViewModel.upDateWin(2)
            score = 10 / (1/(abs(angle!!.minus(334))))
            Log.i("DIR", "Vau! $score")
        } else MainActivity.sensorViewModel.upDateWin(1)
        2 -> if(MainActivity.sensorViewModel.degree.value!! > 64 && MainActivity.sensorViewModel.degree.value!! < 116) {
            MainActivity.sensorViewModel.upDateWin(2)
            score = 10 / (1/(abs(angle!!.minus(64))))
            Log.i("DIR", "Vau!  $score")
        } else MainActivity.sensorViewModel.upDateWin(1)
        3 -> if(MainActivity.sensorViewModel.degree.value!! > 154 && MainActivity.sensorViewModel.degree.value!! < 206) {
            MainActivity.sensorViewModel.upDateWin(2)
            score = 10 / (1/(abs(angle!!.minus(154))))
            Log.i("DIR", "Vau!  $score")
        } else MainActivity.sensorViewModel.upDateWin(1)
        4 -> if(MainActivity.sensorViewModel.degree.value!! > 244 && MainActivity.sensorViewModel.degree.value!! < 296) {
            MainActivity.sensorViewModel.upDateWin(2)
            score = 10 / (1/(abs(angle!!.minus(244))))
            Log.i("DIR", "Vau!  $score")
        } else MainActivity.sensorViewModel.upDateWin(1)
        else -> MainActivity.sensorViewModel.upDateWin(1)
    }
}

fun chooseDirection() {
    val chosen =  Random.nextInt(1, 5)
    MainActivity.sensorViewModel.upDateChosen(chosen)
    Log.i("DIR", "Chosen: $chosen")
}