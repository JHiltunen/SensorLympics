package com.jhiltunen.sensorlympics.ui.navigator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.sensorlympics.R

@ExperimentalFoundationApi
@Composable
fun MainAppNav() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("main") {
            Column {
                Text(text = stringResource(id = R.string.main))
            }
        }
        composable("stats/") {
            Column {
                Text(text = stringResource(id = R.string.stats))
            }
        }
    }
}