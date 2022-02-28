package com.jhiltunen.sensorlympics.olympicmap


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*

class LocationHandler(private var context: Context, var mapViewModel: MapViewModel) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var _startLocation: MutableLiveData<Location> = MutableLiveData()
    val startLocation: LiveData<Location> = _startLocation

    private var _currentLocation: MutableLiveData<Location> = MutableLiveData()
    val currentLocation: LiveData<Location> = _currentLocation

    private var _lastKnownLocation: MutableLiveData<Location> = MutableLiveData()
    val lastKnownLocation: LiveData<Location> = _lastKnownLocation

    private var _currentSpeed: MutableLiveData<Float> = MutableLiveData()
    val currentSpeed: LiveData<Float> = _currentSpeed

    private var _topSpeed: MutableLiveData<Float> = MutableLiveData()
    val topSpeed: LiveData<Float> = _topSpeed

    private var _totalWalkedDistance: MutableLiveData<Float> = MutableLiveData(0f)
    val totalWalkedDistance: LiveData<Float> = _totalWalkedDistance


    fun getStartLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                _startLocation.postValue(it)
                Log.d(
                    "GEOLOCATION",
                    "last location latitude: ${it?.latitude} and longitude: ${it?.longitude}"
                )
            }
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                if (_startLocation.value == null) {
                    _startLocation.value = _lastKnownLocation.value

                    _lastKnownLocation.value?.let { mapViewModel.updateMapValue(it) }
                }
            }
        }

    }

    fun getMyLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                _lastKnownLocation.postValue(it)
                _currentLocation.postValue(it)

                mapViewModel.updateMapValue(it)
                Log.d(
                    "GEOLOCATION",
                    "Location latitude: ${it?.latitude} and longitude: ${it?.longitude}"
                )
            }


        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                if (_lastKnownLocation.value != null) {
                    _totalWalkedDistance.postValue(
                        _totalWalkedDistance.value?.plus(
                            _lastKnownLocation.value!!.distanceTo(locationResult.lastLocation)
                        )
                    )
                }

                // or _ ?
                currentLocation.value?.let { mapViewModel.updateMapValue(it) }

                Log.d("LocationCallBack", "${locationResult.locations.size}")

                _currentLocation.postValue(locationResult.lastLocation)
                _currentSpeed.postValue(locationResult.lastLocation.speed)
                if (_topSpeed.value == null && _currentSpeed.value != null) {
                    _topSpeed.value = _currentSpeed.value
                }
                if (_topSpeed.value != null && _currentSpeed.value != null) {
                    if (_currentSpeed.value!! > _topSpeed.value!!) {
                        _topSpeed.value = _currentSpeed.value
                    }
                }
            }
        }
    }


    fun startTracking() {
        val locationRequest = LocationRequest
            .create()
            .setInterval(1000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    fun stopTracking() {
        val removeLocationUpdates = fusedLocationClient.removeLocationUpdates(locationCallback)
        _currentSpeed.postValue(0.0f)
        _totalWalkedDistance.postValue(0.0f)
        Log.d("GEOTRACK", "Tracking should stop")
    }

}

