package com.jhiltunen.sensorlympics.menu

import com.jhiltunen.sensorlympics.magnetgame.SensorMagnetApp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.ballgame.BallGameViewModel
import com.jhiltunen.sensorlympics.pressuregame.PressureApp
import com.jhiltunen.sensorlympics.ui.theme.Purple200
import com.jhiltunen.sensorlympics.ui.views.TicTacToeView
import com.jhiltunen.sensorlympics.tictactoe.TicTacToeViewModel
import com.jhiltunen.sensorlympics.ui.views.BallGameView
import kotlinx.coroutines.launch

@Composable
fun Menu(screen: DrawerAppScreen, ballGameViewModel: BallGameViewModel?) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val currentScreen = remember { mutableStateOf(screen) }
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            DrawerContentComponent(
                currentScreen = currentScreen,
                closeDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        },
        content = {
            BodyContentComponent(
                ballGameViewModel = ballGameViewModel,
                currentScreen = currentScreen.value,
                openDrawer = {
                    coroutineScope.launch { drawerState.open() }

                }
            )
        }
    )
    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setStatusBarColor(
            color = Purple200,
            darkIcons = useDarkIcons
        )
    }
}


@Composable
fun DrawerContentComponent(
    currentScreen: MutableState<DrawerAppScreen>,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        for (index in DrawerAppScreen.values().indices) {
            val screen = getScreenBasedOnIndex(index)
            Column(Modifier.clickable(onClick = {
                currentScreen.value = screen
                closeDrawer()
            }), content = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = if (currentScreen.value == screen) {
                        // Hover colors in menu
                        MaterialTheme.colors.secondary
                    } else {
                        MaterialTheme.colors.surface
                    }
                ) {
                    Text(text = screen.name, modifier = Modifier.padding(16.dp))
                }
            })
        }
    }
}

// Returns the corresponding DrawerAppScreen based on the index passed to it.
fun getScreenBasedOnIndex(index: Int) = when (index) {
    0 -> DrawerAppScreen.Screen1
    1 -> DrawerAppScreen.Screen2
    2 -> DrawerAppScreen.Screen3
    3 -> DrawerAppScreen.Screen4
    else -> DrawerAppScreen.Screen1
}

// Passed the corresponding screen composable based on the current screen that's active.
@Composable
fun BodyContentComponent(
    ballGameViewModel: BallGameViewModel?,
    currentScreen: DrawerAppScreen,
    openDrawer: () -> Unit
) {
    when (currentScreen) {
        DrawerAppScreen.Screen1 -> Screen1(
            openDrawer
        )
        DrawerAppScreen.Screen2 -> Screen2(
            openDrawer
        )
        DrawerAppScreen.Screen3 -> Screen3(
            openDrawer
        )
        DrawerAppScreen.Screen4 -> Screen4(ballGameViewModel = ballGameViewModel!!, openDrawer)
    }
}

@Composable
fun Screen1(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(R.string.title1)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
        Surface(
            color = Color(0xFFffd7d7.toInt()),
            modifier = Modifier
                .weight(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    SensorMagnetApp(LocalContext.current)
                }
            )
        }
    }
}

@Composable
fun Screen2(openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(R.string.title2)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
        Surface(
            color = Color(0xFFffe9d6.toInt()),
            modifier = Modifier
                .weight(1f)
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    PressureApp()
                }
            )
        }
    }
}

@Composable
fun Screen3(openDrawer: () -> Unit) {
    val ticTacToe = TicTacToeViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(R.string.title3)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
        Surface(
            color = Color(0xFFfffbd0.toInt()),
            modifier = Modifier.weight(1f)
        )
        {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    TicTacToeView(ticTacToe)
                }
            )
        }
    }
}

@Composable
fun Screen4(ballGameViewModel: BallGameViewModel, openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(R.string.title4)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
        Surface(color = Color(0xFFfffbd0.toInt()), modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    BallGameView(ballGameViewModel = ballGameViewModel)
                }
            )
        }
    }
}

enum class DrawerAppScreen {
    Screen1,
    Screen2,
    Screen3,
    Screen4
}
