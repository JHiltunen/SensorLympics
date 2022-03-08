package com.jhiltunen.sensorlympics.viewmodels

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhiltunen.sensorlympics.BluetoothActivity
import com.jhiltunen.sensorlympics.utils.BluetoothConnectionService

class BluetoothViewModel() : ViewModel() {
    val mBTDevices: MutableLiveData<List<BluetoothDevice?>> = MutableLiveData(mutableListOf())
    val selectedDevice: MutableLiveData<BluetoothDevice> = MutableLiveData()
    var mBluetoothAdapter: BluetoothAdapter? = BluetoothActivity.mBluetoothAdapter
    var mBluetoothConnection: BluetoothConnectionService? = null
    var isConnected: MutableLiveData<Boolean> = MutableLiveData(false)

    fun setBluetoothConnection(bluetoothActivity: BluetoothActivity) {
        mBluetoothConnection = BluetoothConnectionService(bluetoothActivity)
    }
}