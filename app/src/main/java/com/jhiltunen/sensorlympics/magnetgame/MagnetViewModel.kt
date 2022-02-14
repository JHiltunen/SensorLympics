package com.jhiltunen.sensorlympics.magnetgame

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhiltunen.sensorlympics.MainActivity.Companion.sensorViewModel
import com.jhiltunen.sensorlympics.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.jhiltunen.sensorlympics.ui.theme.Purple500
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme

import kotlin.random.Random

class SensorViewModel : ViewModel() {
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
    fun upDateChosen(chosen: Int) {
        _chosen.value = chosen
    }
}


