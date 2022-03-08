package com.jhiltunen.sensorlympics.ui.views

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity.Companion.ballGameViewModel
import com.jhiltunen.sensorlympics.SpaceBetweenColumn

@ExperimentalFoundationApi
@Composable
fun BallGameView() {
    val xPosition by ballGameViewModel.xPosition.observeAsState()
    val yPosition by ballGameViewModel.yPosition.observeAsState()

    Surface(color = MaterialTheme.colors.background) {
                    Canvas(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                        if (ballGameViewModel.xMax != size.width && ballGameViewModel.yMax != size.height) {
                            ballGameViewModel.setMaxValues(size.width, size.height)
                        }

                        if (xPosition!! <= 0 || xPosition!! >= ballGameViewModel.xMax) {
                            ballGameViewModel.updateXAcceleration(0f)
                        }
                        if (yPosition!! <= 0 || yPosition!! >= ballGameViewModel.yMax) {
                            ballGameViewModel.updateYAcceleration(0f)
                        }

                        drawCircle(
                            color = Color.Red,
                            radius = 45f,
                            center = Offset(
                                x = xPosition!!,
                                y = yPosition!!
                            )
                        )
                    }
            }
}