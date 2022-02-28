package com.jhiltunen.sensorlympics.olympicmap

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun addressGetter2(): String {
    val context = LocalContext.current
    val geocoder = Geocoder(context)
    val list = geocoder.getFromLocation(60.24104,24.73840,1)
    val jotain = list[0].getAddressLine(0)

    Log.i("OSOITE", jotain)
    return jotain
}

@Composable
fun addressGetter3(lat: Double, long: Double): String {
    val context = LocalContext.current
    val geocoder = Geocoder(context)
    val list = geocoder.getFromLocation(lat,long,1)
    //val list = geocoder.getFromLocation(lat,long,1)
    val jotain = list[0].getAddressLine(0)
    Log.i("OSOITE", jotain)
    return jotain
}


fun addressGetter(lat: Double, long: Double, context: Context): String {
    val geocoder = Geocoder(context)
    //val list = geocoder.getFromLocation(60.242213,24.737606,1)
    val list = geocoder.getFromLocation(lat,long,1)
    val jotain = list[0].getAddressLine(0)
    Log.i("OSOITE", jotain)
    return jotain
}