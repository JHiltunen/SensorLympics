package com.jhiltunen.sensorlympics.magnetgame

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.MainActivity.Companion.scoreViewModel
import com.jhiltunen.sensorlympics.room.Score
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
            MainActivity.magnetViewModel.upDateScore(score)
            scoreViewModel.insert(Score(0,"Magneto", score.toLong()))

        } else {
            MainActivity.magnetViewModel.upDateWin(1)
            scoreViewModel.insert(Score(0,"Magneto", 0))
        }

        //East
        2 -> if (MainActivity.magnetViewModel.degree.value!! > 64 && MainActivity.magnetViewModel.degree.value!! < 116) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1 / (abs(angle!!.minus(64))))
            MainActivity.magnetViewModel.upDateScore(score)
            scoreViewModel.insert(Score(0,"Magneto", score.toLong()))

        } else {
            MainActivity.magnetViewModel.upDateWin(1)
            scoreViewModel.insert(Score(0,"Magneto", 0))

        }
        //South
        3 -> if (MainActivity.magnetViewModel.degree.value!! > 154 && MainActivity.magnetViewModel.degree.value!! < 206) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1 / (abs(angle!!.minus(154))))
            MainActivity.magnetViewModel.upDateScore(score)
            scoreViewModel.insert(Score(0,"Magneto", score.toLong()))

        } else {
            MainActivity.magnetViewModel.upDateWin(1)
            scoreViewModel.insert(Score(0,"Magneto", 0))
        }
        //West
        4 -> if (MainActivity.magnetViewModel.degree.value!! > 244 && MainActivity.magnetViewModel.degree.value!! < 296) {
            MainActivity.magnetViewModel.upDateWin(2)
            score = 10 / (1 / (abs(angle!!.minus(244))))
            MainActivity.magnetViewModel.upDateScore(score)
            scoreViewModel.insert(Score(0,"Magneto", score.toLong()))

        } else {
            MainActivity.magnetViewModel.upDateWin(1)
            scoreViewModel.insert(Score(0,"Magneto", 0))
        }
        else -> MainActivity.magnetViewModel.upDateWin(1)
    }
}

@ExperimentalFoundationApi
fun chooseDirection() {
    val chosen = Random.nextInt(1, 5)
    MainActivity.magnetViewModel.upDateChosen(chosen)
}