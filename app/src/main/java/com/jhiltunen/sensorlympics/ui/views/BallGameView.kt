package com.jhiltunen.sensorlympics.ui.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.jhiltunen.sensorlympics.ui.layouts.CardStyle
import com.jhiltunen.sensorlympics.MainActivity.Companion.ballGameViewModel
import com.jhiltunen.sensorlympics.ui.layouts.SpaceBetweenColumn

@ExperimentalFoundationApi
@Composable
fun BallGameView() {
    val xPosition by ballGameViewModel.xPosition.observeAsState()
    val yPosition by ballGameViewModel.yPosition.observeAsState()

    Surface(color = MaterialTheme.colors.background) {
        Card {
            CardStyle {
                SpaceBetweenColumn {
                    Canvas(modifier = Modifier.fillMaxSize()) {
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
        }
    }
}