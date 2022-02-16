package com.jhiltunen.sensorlympics.magnetgame

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.ui.theme.Purple500
import com.jhiltunen.sensorlympics.ui.theme.SensorLympicsTheme


@Composable
fun SensorMagnetApp(context: Context) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var winOrLose by remember { mutableStateOf(false) }
    var direction by remember { mutableStateOf("") }



    SensorLympicsTheme() {
        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                Surface(color = MaterialTheme.colors.background) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        elevation = 10.dp
                    ) {
                        Column(modifier = Modifier.padding(15.dp)) {
                            Button(
                                onClick = {
                                    if (!winOrLose) {
                                        winOrLose = true
                                        val theChosen = MainActivity.magnetViewModel.chosen.value ?: 1
                                        when (theChosen) {
                                            1 -> direction =  context.getString(R.string.north)
                                            2 -> direction =  context.getString(R.string.east)
                                            3 -> direction =  context.getString(R.string.south)
                                            4 -> direction =  context.getString(R.string.west)
                                        }
                                        Log.i("DIR", "Onclick chosen: $theChosen")
                                        northOrBust(theChosen)

                                    } else {
                                        winOrLose = false
                                        chooseDirection()
                                        MainActivity.magnetViewModel.upDateWin(0)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (!winOrLose) {
                                    val theChosen = MainActivity.magnetViewModel.chosen.value ?: 1
                                    when (theChosen) {
                                        1 -> direction =  context.getString(R.string.north)
                                        2 -> direction =  context.getString(R.string.east)
                                        3 -> direction =  context.getString(R.string.south)
                                        4 -> direction =  context.getString(R.string.west)
                                    }
                                    Text(stringResource(R.string.btn_start, direction))
                                } else {
                                    Text(stringResource(R.string.btn_again))
                                }
                            }
                            Spacer(Modifier.height(15.dp))
                            ShowWinOrLose(MainActivity.magnetViewModel)
                            //ShowSenorData(MainActivity.sensorViewModel)
                            FeaturedCircularProgressIndicator(MainActivity.magnetViewModel)
                        }
                    }
                }
            }
        )
    }
}




@Composable
fun ShowWinOrLose(magnetViewModel: MagnetViewModel) {
    val winOrLoseOr by magnetViewModel.win.observeAsState()
    val chosen by magnetViewModel.chosen.observeAsState()
    val highScore by  magnetViewModel.highScore.observeAsState()
    val score by magnetViewModel.score.observeAsState()
    val direction: String
    when (chosen) {
        1 -> direction =  stringResource(R.string.north)
        2 -> direction =  stringResource(R.string.east)
        3 -> direction =  stringResource(R.string.south)
        4 -> direction =  stringResource(R.string.west)
        else  -> direction =  ""
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        when (winOrLoseOr) {
            0 -> Text(stringResource(R.string.result_not_yet), Modifier.padding(8.dp))
            1 -> Text(stringResource(R.string.result_bad), Modifier.padding(8.dp))
            2 -> Text(stringResource(R.string.result_good, direction, score ?: 0), Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(7.dp))

        Text("Highscore: ${highScore ?: 0}")

    }
}

@Composable
fun FeaturedCircularProgressIndicator(magnetViewModel: MagnetViewModel) {
    val degree by magnetViewModel.degree.observeAsState()
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight(fraction = 0.5f)
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.5f),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text((stringResource(R.string.currentMaxMin)))
            Image(
                painter = painterResource(id = R.drawable.me_bw),
                contentDescription = null,
                modifier = Modifier
                    .rotate(degree ?: 0f)
                    .background(Color.Red)
                    .size(50.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Box (modifier = Modifier
                .rotate(degree ?: 0f)
                .background(Color.Red)
                .size(50.dp))
            Spacer(modifier = Modifier.height(7.dp))

            CircularProgressIndicator(
                progress = degree?.div(360) ?: 0.0f,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.25f),
                color = Purple500,
                strokeWidth = 5.dp
            )
        }
    }
}


/*
@Composable
fun ShowSenorData(sensorViewModel: com.jhiltunen.sensorlympics.magnetgame.SensorViewModel) {
   val value by sensorViewModel.value.observeAsState()
   val xX by sensorViewModel.xX.observeAsState()
   val yY by sensorViewModel.yY.observeAsState()
   var angle = yY?.let { xX?.let { it1 -> atan2(it, it1) } }

   var angleThe = (PI / 2)
   Log.i("SENOR", value.toString())

   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.SpaceEvenly,
   ) {
       Text(value ?: "", Modifier.padding(8.dp))
       Spacer(modifier = Modifier.height(7.dp))
       Text(xX.toString() ?: "", Modifier.padding(8.dp))
   }
}
*/

