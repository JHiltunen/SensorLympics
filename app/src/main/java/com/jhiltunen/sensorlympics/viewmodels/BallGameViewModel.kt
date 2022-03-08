package com.jhiltunen.sensorlympics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BallGameViewModel : ViewModel() {
    private val _xPosition = MutableLiveData(0f)
    var xPosition: LiveData<Float> = _xPosition
    private val _yPosition = MutableLiveData(0f)
    var yPosition: LiveData<Float> = _yPosition

    var xMax: Float = 0f
    var yMax: Float = 0f

    private var xAcceleration: Float = 0f
    private var xVelocity: Float = 0.0f

    private var yAcceleration: Float = 0f
    private var yVelocity: Float = 0.0f

    fun updateBall() {
        val frameTime = 0.666f
        xVelocity += xAcceleration * frameTime
        yVelocity += yAcceleration * frameTime
        val xS: Float = xVelocity / 2 * frameTime
        val yS: Float = yVelocity / 2 * frameTime

        viewModelScope.launch {
            _xPosition.postValue(xPosition.value?.minus(xS))
            _yPosition.postValue(yPosition.value?.minus(yS))
        }

        if (xPosition.value!! > xMax) {
            _xPosition.postValue(xMax)
        } else if (xPosition.value!! < 0) {
            _xPosition.postValue(0f)
        }
        if (yPosition.value!! > yMax) {
            _yPosition.postValue(yMax)
        } else if (yPosition.value!! < 0) {
            _yPosition.postValue(0f)
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