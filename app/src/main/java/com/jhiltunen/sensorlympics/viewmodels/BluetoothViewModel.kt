package com.jhiltunen.sensorlympics.viewmodels

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BluetoothViewModel : ViewModel() {
    val mBTDevices: MutableLiveData<List<BluetoothDevice?>> = MutableLiveData(mutableListOf())
}