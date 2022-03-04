package com.jhiltunen.sensorlympics.magnetgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MagnetViewModel : ViewModel() {
    private val _value: MutableLiveData<String> = MutableLiveData()
    val value: LiveData<String> = _value

    private val _xX: MutableLiveData<Float> = MutableLiveData()
    val xX: LiveData<Float> = _xX

    private val _yY: MutableLiveData<Float> = MutableLiveData()
    val yY: LiveData<Float> = _yY

    private val _degree: MutableLiveData<Float> = MutableLiveData()
    val degree: LiveData<Float> = _degree

    private val _win: MutableLiveData<Int> = MutableLiveData()
    val win: LiveData<Int> = _win

    private val _chosen: MutableLiveData<Int> = MutableLiveData()
    val chosen: LiveData<Int> = _chosen

    private val _score: MutableLiveData<Float> = MutableLiveData()
    val score: LiveData<Float> = _score

    private val _highScore: MutableLiveData<Float> = MutableLiveData()
    val highScore: LiveData<Float> = _highScore

    fun updateValue(value: String, xValue: Float, yValue: Float) {
        _value.value = value
        _xX.value = xValue
        _yY.value = yValue
    }

    fun upDateDegree(degree: Float) {
        _degree.value = degree
    }

    fun upDateWin(win: Int) {
        _win.value = win
    }

    fun upDateScore(score: Float) {
        _score.value = score
        if (_highScore.value == null) {
            _highScore.value = score
        }
        if (_score.value!! > _highScore.value!!) {
            _highScore.value = score
        }
    }

    fun upDateChosen(chosen: Int) {
        _chosen.value = chosen
    }
}


