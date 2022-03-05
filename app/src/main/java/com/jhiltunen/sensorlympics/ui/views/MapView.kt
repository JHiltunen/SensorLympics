package com.jhiltunen.sensorlympics.api


import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.MainActivity.Companion.receiverViewModel
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.utils.GlobalModel.cities
import com.jhiltunen.sensorlympics.viewmodels.ReceiverViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider


@Composable
fun composeMap(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }
    return mapView
}

@ExperimentalFoundationApi
@Composable
fun ShowMap(mapViewModel: MapViewModel, context: Context, model: WeatherViewModel, receiverViewModel: ReceiverViewModel) {
    val map = composeMap()
    val totalhits: Double? by model.changeNotifier.observeAsState(0.0)
    var cityName by remember { mutableStateOf("") }
    val mapInitialized by remember(map) { mutableStateOf(false) }
    val address by mapViewModel.mapData.observeAsState()
    val airplane: Boolean? by MainActivity.receiverViewModel.airplane.observeAsState()

    if (!mapInitialized) {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(4.5)
        map.controller.setCenter(GeoPoint(60.166640739, 24.943536799))
    }
    Log.i("AIRO", "$airplane")
    Card {
        CardStyle {
            Column {
                if (!airplane!!) {
                    Spacer(modifier = Modifier.height(4.dp))
                    if (totalhits!! > 0) {
                        Text(totalhits.toString())
                        Text(cityName)
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    AndroidView({ map }) {
                        address ?: return@AndroidView
                        val dm: DisplayMetrics = context.resources.displayMetrics

                        val mCompassOverlay =
                            CompassOverlay(context, InternalCompassOrientationProvider(context), map)
                        mCompassOverlay.enableCompass()

                        val scaleBarOverlay = ScaleBarOverlay(map)
                        scaleBarOverlay.setCentred(true)
                        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 20)

                        map.setMultiTouchControls(true)

                        map.overlays.add(mCompassOverlay)
                        map.overlays.add(scaleBarOverlay)

                        cities.forEach {
                            val cityMarker = Marker(map)
                            cityMarker.setOnMarkerClickListener { _, _ ->
                                if (cityMarker.isInfoWindowShown) {
                                    cityMarker.closeInfoWindow()
                                } else {
                                    model.getHits(it.city)
                                    cityName = it.city
                                    cityMarker.closeInfoWindow()
                                }
                                true
                            }
                            cityMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            cityMarker.position.latitude = it.latiTude
                            cityMarker.position.longitude = it.longiTude

                            cityMarker.title = it.city
                            map.overlays.add(cityMarker)
                            map.invalidate()
                        }
                    }
                } else {
                    Text(stringResource(R.string.map_not))
                }

            }
        }
    }
}