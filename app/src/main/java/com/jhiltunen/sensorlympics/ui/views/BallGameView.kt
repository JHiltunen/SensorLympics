package com.jhiltunen.sensorlympics.ui.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.MainActivity.Companion.ballGameViewModel
import com.jhiltunen.sensorlympics.MainActivity.Companion.scoreViewModel
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.room.Score
import kotlin.math.abs


const val BALL_RADIUS = 45f


@ExperimentalFoundationApi
@Composable
fun BallGameView() {
    val xPosition by ballGameViewModel.xPosition.observeAsState()
    val yPosition by ballGameViewModel.yPosition.observeAsState()
    val ballHighScore by scoreViewModel.getHighscore("Ball").observeAsState()

    var gameOn by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }

    var xRandom = 0f
    var yRandom = 0f

    Surface(color = MaterialTheme.colors.background) {
        if (MainActivity.accelerometerSensorExists) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()

                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(16.dp)) {
                                Text(stringResource(R.string.pressure_high, (ballHighScore ?: 0)))
                                if (!gameOn && gameOver) {
                                    val score =
                                        ((abs(ballGameViewModel.xAcceleration) + abs(ballGameViewModel.yAcceleration)) * 20).toInt()
                                    Text(stringResource(R.string.ball_score, score))
                                    scoreViewModel.insert(Score(0,"Ball", score.toLong()))
                                }
                                Spacer(modifier = Modifier.padding(8.dp))
                                BallGameRules()
                                Button(
                                    onClick = {
                                        gameOn = !gameOn
                                        if (gameOn) {
                                            ballGameViewModel.upDateGameOn(true)
                                            xRandom =
                                                (100..(ballGameViewModel.xMax.toInt() - 200)).random().toFloat()
                                            yRandom =
                                                (100..(ballGameViewModel.yMax.toInt() - 200)).random().toFloat()
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
                            }
                        }

                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            if (ballGameViewModel.xMax != size.width && ballGameViewModel.yMax != size.height && gameOn) {
                                ballGameViewModel.setMaxValues(size.width, size.height)
                            }

                            if (abs(xPosition?.toInt()!! - xRandom.toInt()) < 30 && abs(yPosition?.toInt()!! - yRandom.toInt()) < 30) {
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
                                        size = Size(20f, 20f)
                                    )
                                }
                            }

                        }
                }
        } else {
            Text(text = stringResource(R.string.buy_new_phone))
        }
    }
}