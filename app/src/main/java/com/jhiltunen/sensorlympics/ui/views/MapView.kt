package com.jhiltunen.sensorlympics.ui.views


import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.api.MapViewModel
import com.jhiltunen.sensorlympics.api.WeatherViewModel
import com.jhiltunen.sensorlympics.utils.GlobalModel.cities
import com.jhiltunen.sensorlympics.utils.isOnline
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import kotlin.math.roundToInt


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
fun ShowMap(mapViewModel: MapViewModel, context: Context, model: WeatherViewModel) {
    val map = composeMap()
    val totalHits: Double? by model.changeNotifier.observeAsState(0.0)
    var cityName by remember { mutableStateOf("") }
    val mapInitialized by remember(map) { mutableStateOf(false) }
    val address by mapViewModel.mapData.observeAsState()
    val airplane: Boolean? by MainActivity.receiverViewModel.airplane.observeAsState()

    if (!mapInitialized) {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(4.5)
        map.controller.setCenter(GeoPoint(60.166640739, 24.943536799))
    }

    Card {
        CardStyle {
            Column {
                if (!airplane!! && isOnline(context)) {
                    Spacer(modifier = Modifier.height(4.dp))
                    if (totalHits!! > 0) {
                        Row(
                            Modifier.padding(4.dp)
                        ) {
                            Text(text = "City:", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(cityName)
                        }
                        Row(
                            Modifier.padding(4.dp)
                        ) {
                            Text(text = "Current Temperature:", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(((totalHits!! - 273.15).roundToInt()).toString())
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(text = "°C")
                        }
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
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(stringResource(R.string.map_not),
                            modifier = Modifier
                                .padding(15.dp)
                    )
                }
            }
        }
    }
}