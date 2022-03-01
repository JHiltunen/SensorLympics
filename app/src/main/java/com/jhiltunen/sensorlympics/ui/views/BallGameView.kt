package com.jhiltunen.sensorlympics.ui.views

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.jhiltunen.sensorlympics.ballgame.BallGameViewModel
import kotlinx.coroutines.launch

@Composable
fun BallGameView(ballGameViewModel: BallGameViewModel) {
    val xPosition by ballGameViewModel.xPosition.observeAsState()
    val yPosition by ballGameViewModel.yPosition.observeAsState()

    Log.d("PATEN PELIT", "xPosition: $xPosition")

    // This scope will be canceled when the composable leaves the composition
    val animationScope = rememberCoroutineScope()

    // You could also give an offset as an initial value and
    // Offset.VectorConverter as a typeConverter. For the simplicity
    // lets just use a different float value for x and y
    val animatableX = remember { Animatable(initialValue = xPosition!!) }
    val animatableY = remember { Animatable(initialValue = yPosition!!) }


    Canvas(modifier = Modifier.fillMaxSize().clickable {
        animationScope.launch {
            // Start the animations without blocking each other
            // On each click x and y values will be created randomly
            launch {
                animatableX.animateTo(
                    targetValue = (0..1000)
                        .random()
                        .toFloat(),
                    animationSpec = tween(durationMillis = 1000)
                )
            }

            launch {
                animatableY.animateTo(
                    targetValue = (0..1000)
                        .random()
                        .toFloat(),
                    animationSpec = tween(durationMillis = 1000)
                )
            }
        }
    }) {
       drawCircle(Color.Red, center = Offset(animatableX.value, animatableY.value), radius = 40f)
        drawCircle(
            color = Color.Red,
            radius = 45f,
            center = Offset(
                x = animatableX.value,
                y = animatableY.value
            )
        )
    }
}