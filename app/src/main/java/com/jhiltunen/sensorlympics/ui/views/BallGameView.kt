package com.jhiltunen.sensorlympics.ui.views

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.MainActivity.Companion.ballGameViewModel
import com.jhiltunen.sensorlympics.R
import kotlin.random.Random

@ExperimentalFoundationApi
@Composable
fun BallGameView() {
    val xPosition by ballGameViewModel.xPosition.observeAsState()
    val yPosition by ballGameViewModel.yPosition.observeAsState()
    //(0..ballGameViewModel.xMax.toInt()).random().toFloat(), (0..ballGameViewModel.yMax.toInt()).random().toFloat()

    val option = BitmapFactory.Options()
    option.inPreferredConfig = Bitmap.Config.ARGB_8888
    val bitmap = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.img,
        option
    ).asImageBitmap()

    Surface(color = MaterialTheme.colors.background) {
        Card {
            CardStyle {
                var xRandom: Float = 0f
                var yRandom: Float = 0f
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    if (ballGameViewModel.xMax != size.width && ballGameViewModel.yMax != size.height) {
                        ballGameViewModel.setMaxValues(size.width, size.height)
                        xRandom = (0..ballGameViewModel.xMax.toInt()).random().toFloat()
                        yRandom = (0..ballGameViewModel.xMax.toInt()).random().toFloat()
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

                    /*drawImage(
                        image = bitmap,
                        topLeft = Offset(randomX, randomY)
                    )*/

                    drawRect(Color.Blue, topLeft = Offset(xRandom, yRandom), size = Size(200f, 200f))
                }
            }
        }
    }
}