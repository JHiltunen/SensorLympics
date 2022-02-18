package com.jhiltunen.sensorlympics.navigator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.sensorlympics.layout.Grid
import com.jhiltunen.sensorlympics.menu.MagnetoGame
import com.jhiltunen.sensorlympics.menu.Menu

@ExperimentalFoundationApi
@Composable
fun MainAppNav() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("main") {
            Column {
                Grid()
/*                Button(onClick = { navController.navigate("stats") }) {
                    Text(stringResource(R.string.game1))
                }*/
            }
        }
        composable("stats") {
            Menu()
        }
    }
}

