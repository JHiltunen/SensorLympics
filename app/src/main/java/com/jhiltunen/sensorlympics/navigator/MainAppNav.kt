package com.jhiltunen.sensorlympics.navigator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.menu.DrawerAppScreen
import com.jhiltunen.sensorlympics.menu.Menu
import com.jhiltunen.sensorlympics.ui.layouts.CardStyle
import com.jhiltunen.sensorlympics.ui.theme.*


@ExperimentalFoundationApi
@Composable
fun MainAppNav() {

    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    NavHost(navController, startDestination = "main") {
        composable("main") {
            SideEffect {
                // Update all of the system bar colors to be transparent
                systemUiController.setStatusBarColor(
                    color = YellowRed,
                )
            }
            CardStyle {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = stringResource(id = R.string.app_name), fontSize = 40.sp)
                    Image(
                        painter = painterResource(R.drawable.olympic_rings_1_2),
                        contentDescription = stringResource(R.string.app_logo_text),
                    )
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { navController.navigate("magnetogame") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = BlueCrayola,
                                backgroundColor = Ivory
                            )
                        ) {
                            Text(stringResource(R.string.magneto_game))
                        }

                        Button(
                            onClick = { navController.navigate("pressuregame") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = BrightYellow,
                                backgroundColor = Ivory
                            )
                        ) {
                            Text(stringResource(R.string.pressure_game))
                        }

                        Button(
                            onClick = { navController.navigate("tictactoe") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = RichBlack,
                                backgroundColor = Ivory
                            )
                        ) {
                            Text(stringResource(R.string.tictactoe_game))
                        }

                        Button(
                            onClick = { navController.navigate("ballgame") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = SpanishGreen,
                                backgroundColor = Ivory
                            )
                        ) {
                            Text(stringResource(id = R.string.ball_game))
                        }

                        Button(
                            onClick = { navController.navigate("olympicscities") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = RedCrayola,
                                backgroundColor = Ivory
                            )
                        ) {
                            Text(text = stringResource(id = R.string.olympic_cities))
                        }
                    }
                    Button(
                        onClick = { navController.navigate("statistics") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    {
                        Text(text = stringResource(id = R.string.statistics))
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

        composable("ballgame") {
            Menu(screen = DrawerAppScreen.BallGame)
        }
        composable("olympicscities") {
            Menu(screen = DrawerAppScreen.OlympicsCities)
        }
        composable("statistics") {
            Menu(screen = DrawerAppScreen.Statistics)
        }

    }
}