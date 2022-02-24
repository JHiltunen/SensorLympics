package com.jhiltunen.sensorlympics.navigator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.menu.DrawerAppScreen
import com.jhiltunen.sensorlympics.menu.Menu

@ExperimentalFoundationApi
@Composable
fun MainAppNav() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("main") {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = 16.dp
            ) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "SensorLympics", fontSize = 30.sp)
                    Image(
                        painter = painterResource(R.drawable.rings),
                        contentDescription = "Application logo",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Card(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth(),
                        elevation = 16.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextButton(
                                onClick = { navController.navigate("magnetogame") },
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(stringResource(R.string.game1))
                            }
                            Spacer(modifier = Modifier.padding(12.dp))

                            OutlinedButton(
                                onClick = { navController.navigate("pressuregame") },
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(stringResource(R.string.game2))
                            }
                            Spacer(modifier = Modifier.padding(12.dp))

                            Button(
                                onClick = { navController.navigate("tictactoe") },
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(stringResource(R.string.game3))
                            }
/*                   Spacer(modifier = Modifier.padding(12.dp))
                    Button(onClick = { navController.navigate("") }) {
                        Text(stringResource(R.string.game4))
                    }*/
                        }
                    }
                }
            }
        }
        composable("magnetogame") {
           Menu(screen = DrawerAppScreen.MagnetoGame)
        }

        composable("pressuregame") {
            Menu(screen = DrawerAppScreen.PressureGame)
        }

        composable("tictactoe") {
            Menu(screen = DrawerAppScreen.TicTacToe)
        }
    }
}