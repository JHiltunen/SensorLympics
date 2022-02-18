package com.jhiltunen.sensorlympics.navigator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
            Column {
                Button(onClick = { navController.navigate("magnetogame") }) {
                    Text(stringResource(R.string.game1))
                }
                Button(onClick = { navController.navigate("pressuregame") }) {
                    Text(stringResource(R.string.game2))
                }
                Button(onClick = { navController.navigate("tictactoe") }) {
                    Text(stringResource(R.string.game3))
                }
            }
        }
        composable("magnetogame") {
           Menu(screen = DrawerAppScreen.Screen1)
        }

        composable("pressuregame") {
            Menu(screen = DrawerAppScreen.Screen2)
        }

        composable("tictactoe") {
            Menu(screen = DrawerAppScreen.Screen3)
        }
    }
}