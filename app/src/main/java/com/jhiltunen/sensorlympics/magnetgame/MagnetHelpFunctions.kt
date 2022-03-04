package com.jhiltunen.sensorlympics.magnetgame

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import com.jhiltunen.sensorlympics.MainActivity
import kotlin.math.abs
import kotlin.random.Random


@ExperimentalFoundationApi
fun northOrBust(direction: Int) {
    val score: Float
    val angle = MainActivity.magnetViewModel.degree.value

    when (direction) {
        //North
        1 -> if (MainActivity.magnetViewModel.degree.value!! > 334 || MainActivity.magnetViewModel.degree.value!! < 27) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1 / (abs(angle!!.minus(334))))
            Log.i("DIR", "Vau! $score")
            MainActivity.magnetViewModel.upDateScore(score)

        } else MainActivity.magnetViewModel.upDateWin(1)
        //East
        2 -> if (MainActivity.magnetViewModel.degree.value!! > 64 && MainActivity.magnetViewModel.degree.value!! < 116) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1 / (abs(angle!!.minus(64))))
            Log.i("DIR", "Vau!  $score")
            MainActivity.magnetViewModel.upDateScore(score)

        } else MainActivity.magnetViewModel.upDateWin(1)
        //South
        3 -> if (MainActivity.magnetViewModel.degree.value!! > 154 && MainActivity.magnetViewModel.degree.value!! < 206) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1 / (abs(angle!!.minus(154))))
            Log.i("DIR", "Vau!  $score")
            MainActivity.magnetViewModel.upDateScore(score)

        } else MainActivity.magnetViewModel.upDateWin(1)
        //West
        4 -> if (MainActivity.magnetViewModel.degree.value!! > 244 && MainActivity.magnetViewModel.degree.value!! < 296) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1 / (abs(angle!!.minus(244))))
            Log.i("DIR", "Vau!  $score")
            MainActivity.magnetViewModel.upDateScore(score)

        } else MainActivity.magnetViewModel.upDateWin(1)
        else -> MainActivity.magnetViewModel.upDateWin(1)
    }
}

@ExperimentalFoundationApi
fun chooseDirection() {
    val chosen = Random.nextInt(1, 5)
    MainActivity.magnetViewModel.upDateChosen(chosen)
}