package com.jhiltunen.sensorlympics.ui.views

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.MainActivity.Companion.ballGameViewModel
import com.jhiltunen.sensorlympics.R
import kotlin.math.abs

const val BALL_RADIUS = 45f
@ExperimentalFoundationApi
@Composable
fun BallGameView() {
    val xPosition by ballGameViewModel.xPosition.observeAsState()
    val yPosition by ballGameViewModel.yPosition.observeAsState()

    var gameOn by remember { mutableStateOf(false)}
    var gameOver by remember { mutableStateOf(false)}
    //(0..ballGameViewModel.xMax.toInt()).random().toFloat(), (0..ballGameViewModel.yMax.toInt()).random().toFloat()

    var xRandom = 0f
    var yRandom = 0f

    Surface(color = MaterialTheme.colors.background) {
        if (MainActivity.accelerometerSensorExists) {
            Card {
                Column {
                    Spacer(modifier = Modifier.padding(16.dp))
                    if (!gameOn && gameOver) {
                        val score =
                        Text("Skuubiduu ja löysät pois! Pisteitä tuli: ${((abs(ballGameViewModel.xAcceleration) + abs(ballGameViewModel.yAcceleration))*20).toInt()}")
                    }
                    Button(
                        onClick = {
                            gameOn = !gameOn
                            if (gameOn) {
                                ballGameViewModel.upDateGameOn(true)
                                xRandom = (200..(ballGameViewModel.xMax.toInt() -200)).random().toFloat()
                                yRandom = (200..(ballGameViewModel.yMax.toInt() -200)).random().toFloat()
                                gameOver = false
                            } else {
                                ballGameViewModel.upDateGameOn(false)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        if (!gameOn) {
                            Text(text = stringResource(id = R.string.pressure_press))
                        } else {
                            Text(text = stringResource(id = R.string.pressure_quit))
                        }
                    }
                    CardStyle {
                        //var xRandom: Float = 0f
                        //var yRandom: Float = 0f
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            if (ballGameViewModel.xMax != size.width && ballGameViewModel.yMax != size.height && gameOn) {
                                ballGameViewModel.setMaxValues(size.width, size.height)
                                //xRandom = (0..ballGameViewModel.xMax.toInt()).random().toFloat()
                                //yRandom = (0..ballGameViewModel.xMax.toInt()).random().toFloat()
                            }

                            /*if (xPosition!! <= 0 || xPosition!! >= ballGameViewModel.xMax) {
                        ballGameViewModel.updateXAcceleration(0f)
                    }
                    if (yPosition!! <= 0 || yPosition!! >= ballGameViewModel.yMax) {
                        ballGameViewModel.updateYAcceleration(0f)
                    }*/
                            /*drawImage(
                        image = bitmap,
                        topLeft = Offset(randomX, randomY)
                    )*/
                            if (abs(xPosition?.toInt()!! - xRandom.toInt() ) < 30 && abs(yPosition?.toInt()!!  - yRandom.toInt()) < 30) {
                                ballGameViewModel.upDateGameOn(false)
                                gameOn = false
                                gameOver = true
                            } else {
                                if (gameOn) {
                                    drawCircle(
                                        color = Color.Red,
                                        radius = BALL_RADIUS,
                                        center = Offset(
                                            x = xPosition!!,
                                            y = yPosition!!
                                        )
                                    )
                                    drawRect(
                                        Color.Blue,
                                        topLeft = Offset(xRandom, yRandom),
                                        size = Size(200f, 200f)
                                    )
                                }
                            }

                        }
                    }

                }
            }
        } else {
            Text(text = stringResource(R.string.buy_new_phone))
        }
    }
}