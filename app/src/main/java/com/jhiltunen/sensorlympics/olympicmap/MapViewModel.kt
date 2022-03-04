package com.jhiltunen.sensorlympics.olympicmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.osmdroid.util.GeoPoint

class MapViewModel : ViewModel() {

    private var _mapData: MutableLiveData<MapData> = MutableLiveData()
    var mapData: LiveData<MapData> = _mapData
    private var startPoint: GeoPoint = GeoPoint(60.2412863, 24.7383500)
    private var address: String = ""

    init {
        _mapData.value = MapData(startPoint, address)
    }
}

data class MapData(val geoPoint: GeoPoint, val address: String)