package com.jhiltunen.sensorlympics.ballgame

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BallGameViewModel() : ViewModel() {
    var xMax = 0f
    var yMax: Float = 0f
    val xPosition: MutableLiveData<Float> = MutableLiveData(0f)
    val yPosition: MutableLiveData<Float> = MutableLiveData(0f)

    var xAcceleration: MutableLiveData<Float> = MutableLiveData(0f)
    var xVelocity: MutableLiveData<Float> = MutableLiveData(0f)
    var yAcceleration:MutableLiveData<Float> = MutableLiveData(0f)
    var yVelocity: MutableLiveData<Float> = MutableLiveData(0f)

    fun updateBall() {
        val frameTime = 0.666f
        xVelocity.postValue(xVelocity.value!! + xAcceleration.value!! * frameTime)
        yVelocity.postValue(yVelocity.value!! + yAcceleration.value!! * frameTime)
        val xS: Float = xVelocity.value!! / 2 * frameTime
        val yS: Float = yVelocity.value!! / 2 * frameTime
        xPosition.postValue(xPosition.value!! - xS)
        yPosition.postValue(yPosition.value!! - yS)
        if (xPosition.value!! > xMax) {
            xPosition.postValue(xMax)
        } else if (xPosition.value!! < 0) {
            xPosition.postValue(0f)
        }
        if (yPosition.value!! > yMax) {
            yPosition.postValue(yMax)
        } else if (yPosition.value!! < 0) {
            yPosition.postValue(0f)
        }

        Log.d("DBG", "UPDATE")
    }
}