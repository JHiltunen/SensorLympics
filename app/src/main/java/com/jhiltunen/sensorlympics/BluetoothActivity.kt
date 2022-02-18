package com.jhiltunen.sensorlympics

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.jhiltunen.sensorlympics.utils.BluetoothConnectionService
import com.jhiltunen.sensorlympics.viewmodels.BluetoothViewModel
import java.util.*

class BluetoothActivity : ComponentActivity() {
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mBluetoothConnection: BluetoothConnectionService? = null
    var mBTDevice: BluetoothDevice? = null
    var mBTDevices: MutableLiveData<MutableList<BluetoothDevice?>> = MutableLiveData<MutableList<BluetoothDevice?>>(ArrayList())
    private lateinit var bluetoothViewModel: BluetoothViewModel

    // Create a BroadcastReceiver for ACTION_FOUND
    private val mBroadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
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
    private val mBroadcastReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
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
    private val mBroadcastReceiver3: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            Log.d(TAG, "onReceive: ACTION FOUND.")
            if (action == BluetoothDevice.ACTION_FOUND) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                mBTDevices.value?.add(device)
                Log.d("ADD DEVICE", "List odf decixces: ${mBTDevices.value}")
                // Call extension function to notify
                mBTDevices.notifyObserver()

                Log.d(TAG, "onReceive: " + device!!.name + ": " + device.address)
            }
        }
    }

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private val mBroadcastReceiver4: BroadcastReceiver = object : BroadcastReceiver() {
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

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bluetoothViewModel = BluetoothViewModel(mBluetoothConnection)

        //Broadcasts when bond state changes (ie:pairing)
        val filter = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        registerReceiver(mBroadcastReceiver4, filter)
        val bluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter

        setContent {
            Column() {
                Button(onClick = { startDiscoveringBluetoothDevices() }) {
                    Text(text = "Start device discovery")
                }

                DiscoveredDevices(bluetoothViewModel)
            }
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called.")
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver1)
        unregisterReceiver(mBroadcastReceiver2)
        unregisterReceiver(bluetoothViewModel.mBroadcastReceiver3)
        unregisterReceiver(mBroadcastReceiver4)
        //mBluetoothAdapter.cancelDiscovery();
    }

    //create method for starting connection
    //***remember the conncction will fail and app will crash if you haven't paired first
    fun startConnection() {
        startBTConnection(mBTDevice, MY_UUID_INSECURE)
    }

    /**
     * starting chat service method
     */
    fun startBTConnection(device: BluetoothDevice?, uuid: UUID?) {
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.")
        mBluetoothConnection!!.startClient(device, uuid)
    }

    @SuppressLint("MissingPermission")
    fun enableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.")
        }
        if (!mBluetoothAdapter!!.isEnabled) {
            Log.d(TAG, "enableDisableBT: enabling BT.")
            val enableBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(enableBTIntent)
            val BTIntent = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            registerReceiver(mBroadcastReceiver1, BTIntent)
        }
        if (mBluetoothAdapter!!.isEnabled) {
            Log.d(TAG, "enableDisableBT: disabling BT.")
            mBluetoothAdapter!!.disable()
            val BTIntent = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            registerReceiver(mBroadcastReceiver1, BTIntent)
        }
    }

    @SuppressLint("MissingPermission")
    fun btnEnableDisable_Discoverable(view: View?) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.")
        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        startActivity(discoverableIntent)
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
        registerReceiver(mBroadcastReceiver2, intentFilter)
    }

    @SuppressLint("MissingPermission")
    fun btnDiscover(view: View?) {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.")
        if (mBluetoothAdapter!!.isDiscovering) {
            mBluetoothAdapter!!.cancelDiscovery()
            Log.d(TAG, "btnDiscover: Canceling discovery.")

            //check BT permissions in manifest
            checkBTPermissions()
            mBluetoothAdapter!!.startDiscovery()
            val discoverDevicesIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(bluetoothViewModel.mBroadcastReceiver3, discoverDevicesIntent)
        }
        if (!mBluetoothAdapter!!.isDiscovering) {

            //check BT permissions in manifest
            checkBTPermissions()
            mBluetoothAdapter!!.startDiscovery()
            val discoverDevicesIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(bluetoothViewModel.mBroadcastReceiver3, discoverDevicesIntent)
        }
    }

    //@SuppressLint("MissingPermission")
    /*fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        //first cancel discovery because its very memory intensive.
        mBluetoothAdapter!!.cancelDiscovery()
        Log.d(TAG, "onItemClick: You Clicked on a device.")
        val deviceName = mBTDevices[i]!!.name
        val deviceAddress = mBTDevices[i]!!.address
        Log.d(TAG, "onItemClick: deviceName = $deviceName")
        Log.d(
            TAG,
            "onItemClick: deviceAddress = $deviceAddress"
        )

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        Log.d(TAG, "Trying to pair with $deviceName")
        mBTDevices[i]!!.createBond()
        mBTDevice = mBTDevices[i]
        mBluetoothConnection = BluetoothConnectionService()
    }*/

    companion object {
        private const val TAG = "BluetoothActivity"
        private val MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
    }

    @SuppressLint("MissingPermission")
    fun startDiscoveringBluetoothDevices() {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.")
        if (mBluetoothAdapter!!.isDiscovering) {
            mBluetoothAdapter!!.cancelDiscovery()
            Log.d(TAG, "btnDiscover: Canceling discovery.")

            //check BT permissions in manifest
            checkBTPermissions()
            mBluetoothAdapter!!.startDiscovery()
            val discoverDevicesIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(bluetoothViewModel.mBroadcastReceiver3, discoverDevicesIntent)
        }
        if (!mBluetoothAdapter!!.isDiscovering) {

            //check BT permissions in manifest
            checkBTPermissions()
            mBluetoothAdapter!!.startDiscovery()
            val discoverDevicesIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(bluetoothViewModel.mBroadcastReceiver3, discoverDevicesIntent)
        }
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private fun checkBTPermissions() {
        var permissionCheck = checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION")
        permissionCheck += checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION")
        if (permissionCheck != 0) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1001
            ) //Any number
        }
    }

    /*@SuppressLint("MissingPermission")
    fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        //first cancel discovery because its very memory intensive.
        mBluetoothAdapter!!.cancelDiscovery()
        Log.d(TAG, "onItemClick: You Clicked on a device.")
        val deviceName = mBTDevices[i]!!.name
        val deviceAddress = mBTDevices[i]!!.address
        Log.d(TAG, "onItemClick: deviceName = $deviceName")
        Log.d(
            TAG,
            "onItemClick: deviceAddress = $deviceAddress"
        )

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d(TAG, "Trying to pair with $deviceName")
            mBTDevices[i]!!.createBond()
            mBTDevice = mBTDevices[i]
            mBluetoothConnection = BluetoothConnectionService()
        }
    }*/

    // Kotlin Extension function to assign the LiveData to itself
    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = value
    }
}

@SuppressLint("MissingPermission")
@Composable
fun DiscoveredDevices(bluetoothViewModel: BluetoothViewModel) {
    val discoveredDevicesList by bluetoothViewModel.mBTDevices.observeAsState()
    // val bpmList by bluetoothViewModel.bpmList.observeAsState()
    //Log.d("DISCOVERED", "DISCOVERED DEVICES: $discoveredDevicesList")

    /*Column() {
        bpmList?.forEachIndexed { index, i ->
            Text("Index: $index, Item: $i")
        }
    }*/

    discoveredDevicesList?.forEach { device ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                device?.let { Text(text = it.name) }
                device?.let { Text(text = it.address) }
            }
        }
    }
}