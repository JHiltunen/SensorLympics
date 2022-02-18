package com.jhiltunen.sensorlympics.viewmodels

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhiltunen.sensorlympics.utils.BluetoothConnectionService
import java.util.*
import kotlin.collections.ArrayList

const val TAG = "MainActivity"

class BluetoothViewModel(private val mBluetoothConnection: BluetoothConnectionService?): ViewModel() {
    var mBTDevice: BluetoothDevice? = null
    val _mBTDevices: MutableLiveData<MutableList<BluetoothDevice?>> = MutableLiveData<MutableList<BluetoothDevice?>>(ArrayList())
    val mBTDevices: LiveData<MutableList<BluetoothDevice?>> = _mBTDevices
    private val MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
    var bpmList = MutableLiveData<MutableList<Int>>(ArrayList())

    // Create a BroadcastReceiver for ACTION_FOUND
    val mBroadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            // When discovery finds a device
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> Log.d(TAG, "onReceive: STATE OFF")
                    BluetoothAdapter.STATE_TURNING_OFF -> Log.d(
                        TAG,
                        "mBroadcastReceiver1: STATE TURNING OFF"
                    )
                    BluetoothAdapter.STATE_ON -> Log.d(TAG, "mBroadcastReceiver1: STATE ON")
                    BluetoothAdapter.STATE_TURNING_ON -> Log.d(
                        TAG,
                        "mBroadcastReceiver1: STATE TURNING ON"
                    )
                }
            }
        }
    }

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    val mBroadcastReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == BluetoothAdapter.ACTION_SCAN_MODE_CHANGED) {
                val mode =
                    intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR)
                when (mode) {
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> Log.d(
                        TAG, "mBroadcastReceiver2: Discoverability Enabled."
                    )
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections."
                    )
                    BluetoothAdapter.SCAN_MODE_NONE -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections."
                    )
                    BluetoothAdapter.STATE_CONNECTING -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Connecting...."
                    )
                    BluetoothAdapter.STATE_CONNECTED -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Connected."
                    )
                }
            }
        }
    }

    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    val mBroadcastReceiver3: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            Log.d(TAG, "onReceive: ACTION FOUND.")
            if (action == BluetoothDevice.ACTION_FOUND) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                _mBTDevices.value?.add(device)
                Log.d("ADD DEVICE", "List odf decixces: ${_mBTDevices.value}")
                // Call extension function to notify
                _mBTDevices.notifyObserver()

                bpmList.value?.add((0..10).random())
                // Call the extension function to notify
                bpmList.notifyObserver()

                Log.d(TAG, "onReceive: " + device!!.name + ": " + device.address)
            }
        }
    }

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    val mBroadcastReceiver4: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == BluetoothDevice.ACTION_BOND_STATE_CHANGED) {
                val mDevice =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                //3 cases:
                //case1: bonded already
                if (mDevice!!.bondState == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.")
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice
                }
                //case2: creating a bone
                if (mDevice.bondState == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.")
                }
                //case3: breaking a bond
                if (mDevice.bondState == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.")
                }
            }
        }
    }

    /**
     * starting chat service method
     */
    fun startBTConnection() {
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.")
        mBluetoothConnection!!.startClient(mBTDevice, MY_UUID_INSECURE)
    }

    // Kotlin Extension function to assign the LiveData to itself
    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = value
    }
}