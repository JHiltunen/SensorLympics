package com.jhiltunen.sensorlympics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BallGameViewModel : ViewModel() {
    private val _xPosition = MutableLiveData(0f)
    var xPosition: LiveData<Float> = _xPosition
    private val _yPosition = MutableLiveData(0f)
    var yPosition: LiveData<Float> = _yPosition

    private var xMax: Float = 0f
    private var yMax: Float = 0f

    private var xAcceleration: Float = 0f
    private var xVelocity: Float = 0.0f

    private var yAcceleration: Float = 0f
    private var yVelocity: Float = 0.0f

    fun updateBall() {
        val frameTime = 0.222f
        xVelocity += xAcceleration * frameTime
        yVelocity += yAcceleration * frameTime
        val xS: Float = xVelocity / 2 * frameTime
        val yS: Float = yVelocity / 2 * frameTime

        _xPosition.postValue(xPosition.value?.minus(xS) ?: 0f)
        _yPosition.postValue(yPosition.value?.minus(yS) ?: 0f)

        if (xPosition.value!! > xMax) {
            _xPosition.value = xMax
        } else if (xPosition.value!! < 0) {
            _xPosition.value = 0f
        }
        if (yPosition.value!! > yMax) {
            _yPosition.value = yMax
        } else if (yPosition.value!! < 0) {
            _yPosition.value = 0f
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