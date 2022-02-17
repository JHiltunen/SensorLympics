package com.jhiltunen.sensorlympics.pressuregame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PressureViewModel : ViewModel() {
    private val _value: MutableLiveData<String> = MutableLiveData()
    val value: LiveData<String> = _value

    fun updateValue(value: String) {
        _value.value = value
    }
}

class PressureViewModelProgress : ViewModel() {

    private val _value: MutableLiveData<Float> = MutableLiveData()
    val value: LiveData<Float> = _value

    private val _valueMax: MutableLiveData<Float> = MutableLiveData()
    val valueMax: LiveData<Float> = _valueMax

    private val _valueMin: MutableLiveData<Float> = MutableLiveData()
    val valueMin: LiveData<Float> = _valueMin

    private val _score: MutableLiveData<Float> = MutableLiveData()
    val score: LiveData<Float> = _score

    private val _highScore: MutableLiveData<Float> = MutableLiveData()
    val highScore: LiveData<Float> = _highScore


    fun updateValue(value: Float, valueMax: Float, valueMin: Float) {
        _value.value = value
        _valueMax.value = valueMax
        _valueMin.value = valueMin
    }

    fun upDateScore(score: Float) {
        _score.value = score
        if (_highScore.value == null) {
            _highScore.value = score
        }
        if (_score.value!! > _highScore.value!!) {
            _highScore.value = score
            /*
            app.openFileOutput(FILENAME, Context.MODE_PRIVATE).use {
                it.write("${_highScore.value}\n".toByteArray())
            }
             */
        }
    }
}