package com.jhiltunen.sensorlympics.ui.views


import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.SignalWifiConnectedNoInternet4
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jhiltunen.sensorlympics.ui.layouts.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.api.MapViewModel
import com.jhiltunen.sensorlympics.api.WeatherViewModel
import com.jhiltunen.sensorlympics.ui.theme.MaxRed
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
    val temperatureInKelvins: Double? by model.changeNotifier.observeAsState(0.0)
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!airplane!! && isOnline(context)) {
                    Spacer(modifier = Modifier.height(4.dp))
                    if (temperatureInKelvins!! > 0) {
                        Row(
                            Modifier.padding(4.dp)
                        ) {
                            Text(text = stringResource(R.string.city, cityName), fontWeight = FontWeight.Bold)
                        }
                        Row(
                            Modifier.padding(4.dp)
                        ) {
                            Text(text = stringResource(R.string.current_temperature, (temperatureInKelvins!! - 273.15).roundToInt()), fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    AndroidView({ map }) {
                        address ?: return@AndroidView
                        val dm: DisplayMetrics = context.resources.displayMetrics

                        val mCompassOverlay =
                            CompassOverlay(
                                context,
                                InternalCompassOrientationProvider(context),
                                map
                            )
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

                    Card(
                        modifier = Modifier.padding(16.dp),
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                stringResource(R.string.map_not),
                            )
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),

                                ) {
                                Spacer(modifier = Modifier.padding(16.dp))
                                Icon(
                                    Icons.TwoTone.SignalWifiConnectedNoInternet4,
                                    "",
                                    Modifier.size(80.dp),
                                    tint = MaxRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

