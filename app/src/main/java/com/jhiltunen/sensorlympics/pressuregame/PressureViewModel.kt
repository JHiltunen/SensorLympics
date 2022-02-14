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

    fun updateValue(value: Float, valueMax: Float, valueMin: Float) {
        _value.value = value
        _valueMax.value = valueMax
        _valueMin.value = valueMin
    }
}