package com.jhiltunen.sensorlympics.ui.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.SpaceBetweenColumn
import com.jhiltunen.sensorlympics.ballgame.BallGameViewModel
import com.jhiltunen.sensorlympics.pressuregame.FeaturedCircularProgressIndicator

@Composable
fun BallGameView(ballGameViewModel: BallGameViewModel) {
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