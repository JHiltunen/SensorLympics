package com.jhiltunen.sensorlympics.navigator

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.sensorlympics.BluetoothActivity
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.menu.DrawerAppScreen
import com.jhiltunen.sensorlympics.menu.Menu

@ExperimentalFoundationApi
@Composable
fun MainAppNav() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController, startDestination = "main") {
        composable("main") {
            Column {
                Button(onClick = { navController.navigate("stats") }) {
                    Text(stringResource(R.string.game1))
                }
                Button(onClick = { navController.navigate("tictactoe") }) {
                    Text(stringResource(R.string.game3))
                }
                IconButton(onClick = {
                    context.startActivity(Intent(context, BluetoothActivity::class.java))
                }, modifier = Modifier.fillMaxWidth()) {
                    Row {
                        Icon(Icons.Default.Settings, "Bluetooth settings")
                        Text(text = "Bluetooth settings")
                    }
                }
            }
        }
        composable("stats") {
           Menu(screen = DrawerAppScreen.Screen1)
        }

        composable("tictactoe") {
            Menu(screen = DrawerAppScreen.Screen3)
        }
    }
}