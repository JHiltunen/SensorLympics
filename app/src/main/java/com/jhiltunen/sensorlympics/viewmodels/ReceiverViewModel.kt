package com.jhiltunen.sensorlympics.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReceiverViewModel: ViewModel() {
    private val _airplane: MutableLiveData<Boolean> = MutableLiveData()
    val airplane: LiveData<Boolean> = _airplane

    fun updateAirplane(change: Boolean) {
        _airplane.value = change
        Log.i("AIRO", "${airplane.value}")
    }
}

