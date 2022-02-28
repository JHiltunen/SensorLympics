package com.jhiltunen.sensorlympics.olympicmap

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.osmdroid.util.GeoPoint

class MapViewModel : ViewModel() {

    private var _mapData: MutableLiveData<MapData> = MutableLiveData()
    var mapData: LiveData<MapData> = _mapData
    var startPoint: GeoPoint = GeoPoint(60.2412863, 24.7383500)
    var address: String = ""

    init {
        _mapData.value = MapData(startPoint, address)
    }

    fun updateMapValue(newLocation: Location) {
        startPoint.latitude = newLocation.latitude
        startPoint.longitude = newLocation.longitude
        //_mapData.value =  MapData(startPoint, "Default")
        Log.d("GEOGEO", startPoint.latitude.toString())
        Log.d("GEOGÖÖ",  _mapData.value.toString())
    }

}

data class MapData(val geoPoint: GeoPoint, val address: String)