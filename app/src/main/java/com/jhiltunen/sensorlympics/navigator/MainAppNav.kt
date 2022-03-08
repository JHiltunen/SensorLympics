package com.jhiltunen.sensorlympics.navigator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.menu.DrawerAppScreen
import com.jhiltunen.sensorlympics.menu.Menu
import com.jhiltunen.sensorlympics.ui.theme.Black
import com.jhiltunen.sensorlympics.ui.theme.RichBlack
import com.jhiltunen.sensorlympics.ui.theme.YellowRed
import com.jhiltunen.sensorlympics.ui.theme.*


@ExperimentalFoundationApi
@Composable
fun MainAppNav() {

    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    NavHost(navController, startDestination = "main") {
        composable("main") {
            SideEffect {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setStatusBarColor(
                    color = YellowRed,
                    darkIcons = useDarkIcons
                )
            }
            Card {
                CardStyle {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = stringResource(id = R.string.app_name), fontSize = 40.sp)
                        Image(
                            painter = painterResource(R.drawable.olympic_rings_1_2),
                            contentDescription = stringResource(R.string.app_logo_text),
                            modifier = Modifier
                                .clip(CutCornerShape(10.dp))
                        )
                        Column(
                            modifier = Modifier
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedButton(
                                onClick = { navController.navigate("magnetogame") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, color = Black),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = BlueCrayola)
                            ) {
                                Text(stringResource(R.string.magneto_game))
                            }
                            Spacer(modifier = Modifier.padding(12.dp))
                            OutlinedButton(
                                onClick = { navController.navigate("pressuregame") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, color = Black),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = BrightYellow)
                            ) {
                                Text(stringResource(R.string.pressure_game))
                            }
                            Spacer(modifier = Modifier.padding(12.dp))

                            OutlinedButton(
                                onClick = { navController.navigate("tictactoe") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, color = Black),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = RichBlack)
                            ) {
                                Text(stringResource(R.string.tictactoe_game))
                            }
                            Spacer(modifier = Modifier.padding(12.dp))
                            OutlinedButton(
                                onClick = { navController.navigate("ballgame") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, color = Black),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = SpanishGreen)
                            ) {
                                Text(stringResource(id = R.string.ball_game))
                            }
                            Spacer(modifier = Modifier.padding(12.dp))
                            OutlinedButton(
                                onClick = { navController.navigate("olympicscities") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, color = Black),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = RedCrayola)
                            ) {
                                Text(text = stringResource(id = R.string.olympic_cities))
                            }
                        }
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        {
                            Text(text = stringResource(id = R.string.statistics))
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

        composable("ballgame") {
            Menu(screen = DrawerAppScreen.BallGame)
        }
        composable("olympicscities") {
            Menu(screen = DrawerAppScreen.OlympicsCities)
        }

    }
}