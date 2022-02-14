package com.jhiltunen.sensorlympics.magnetgame

import android.util.Log
import com.jhiltunen.sensorlympics.MainActivity
import kotlin.random.Random


fun northOrBust(direction: Int) {
    when (direction) {
        1 -> if(MainActivity.sensorViewModel.degree.value!! > 334 || MainActivity.sensorViewModel.degree.value!! < 27) {
            MainActivity.sensorViewModel.upDateWin(2)
            Log.i("DIR", "Vau!")
        } else MainActivity.sensorViewModel.upDateWin(1)
        2 -> if(MainActivity.sensorViewModel.degree.value!! > 64 && MainActivity.sensorViewModel.degree.value!! < 116) {
            MainActivity.sensorViewModel.upDateWin(2)
            Log.i("DIR", "Vau!")
        } else MainActivity.sensorViewModel.upDateWin(1)
        3 -> if(MainActivity.sensorViewModel.degree.value!! > 154 && MainActivity.sensorViewModel.degree.value!! < 206) {
            MainActivity.sensorViewModel.upDateWin(2)
            Log.i("DIR", "Vau!")
        } else MainActivity.sensorViewModel.upDateWin(1)
        4 -> if(MainActivity.sensorViewModel.degree.value!! > 244 && MainActivity.sensorViewModel.degree.value!! < 296) {
            MainActivity.sensorViewModel.upDateWin(2)
            Log.i("DIR", "Vau!")
        } else MainActivity.sensorViewModel.upDateWin(1)
        else -> MainActivity.sensorViewModel.upDateWin(1)
    }
}

fun chooseDirection() {
    val chosen =  Random.nextInt(1, 5)
    MainActivity.sensorViewModel.upDateChosen(chosen)
    Log.i("DIR", "Chosen: $chosen")
}