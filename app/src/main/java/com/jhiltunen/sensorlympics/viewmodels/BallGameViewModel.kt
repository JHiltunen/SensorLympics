package com.jhiltunen.sensorlympics.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhiltunen.sensorlympics.ui.views.BALL_RADIUS
import kotlinx.coroutines.launch

class BallGameViewModel : ViewModel() {

    private val _gameOn = MutableLiveData(false)
    var gameOn: LiveData<Boolean> = _gameOn

    private val _xPosition = MutableLiveData(550f)
    var xPosition: LiveData<Float> = _xPosition
    private val _yPosition = MutableLiveData(750f)
    var yPosition: LiveData<Float> = _yPosition

    var xMax: Float = 0f
    var yMax: Float = 0f

    var xAcceleration: Float = 0f
    private var xVelocity: Float = 0.0f

    var yAcceleration: Float = 0f
    private var yVelocity: Float = 0.0f

    fun upDateGameOn(isGameOn: Boolean) {
        _gameOn.value = isGameOn
        _xPosition.value = 550f
        _yPosition.value = 750f
    }

    fun updateBall() {
        val frameTime = 0.333f
        xVelocity += xAcceleration * frameTime
        yVelocity += yAcceleration * frameTime
        val xS: Float = xVelocity / 2 * frameTime
        val yS: Float = yVelocity / 2 * frameTime

        var tempX = ""
        var tempY = ""


        viewModelScope.launch {
            if (xPosition.value!! > xMax - BALL_RADIUS) {
                _xPosition.postValue(xMax - BALL_RADIUS)
            } else if (xPosition.value!! < BALL_RADIUS) {
                _xPosition.postValue(BALL_RADIUS)
                xAcceleration = 0f
            } else {
                _xPosition.postValue(xPosition.value?.minus(xS))

            }

            if (yPosition.value!! > yMax - BALL_RADIUS) {
                _yPosition.postValue(yMax - BALL_RADIUS)
            } else if (yPosition.value!! < BALL_RADIUS) {
                _yPosition.postValue(BALL_RADIUS)
                yAcceleration = 0f
            } else {
                _yPosition.postValue(yPosition.value?.minus(yS))

            }

            Log.d("BALLGAMEVIEWMODEL", "HELLO")


        }



    }

    fun setMaxValues(xMax: Float, yMax: Float) {
        this.xMax = xMax
        this.yMax = yMax
    }

    fun updateXAcceleration(xAcceleration: Float) {
        this.xAcceleration = xAcceleration
    }

    fun updateYAcceleration(yAcceleration: Float) {
        this.yAcceleration = yAcceleration
    }
}