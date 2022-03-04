package com.jhiltunen.sensorlympics.menu

import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.ballgame.BallGameViewModel
import com.jhiltunen.sensorlympics.magnetgame.SensorMagnetApp
import com.jhiltunen.sensorlympics.olympicmap.MapViewModel
import com.jhiltunen.sensorlympics.olympicmap.ShowMap
import com.jhiltunen.sensorlympics.olympicmap.WikiViewModel
import com.jhiltunen.sensorlympics.pressuregame.PressureApp
import com.jhiltunen.sensorlympics.tictactoe.TicTacToeViewModel
import com.jhiltunen.sensorlympics.ui.theme.YellowRed
import com.jhiltunen.sensorlympics.ui.views.BallGameView
import com.jhiltunen.sensorlympics.ui.views.TicTacToeView
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
            color = YellowRed,
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
    0 -> DrawerAppScreen.MagnetoGame
    1 -> DrawerAppScreen.PressureGame
    2 -> DrawerAppScreen.TicTacToe
    3 -> DrawerAppScreen.BallGame
    4 -> DrawerAppScreen.Statistics
    5 -> DrawerAppScreen.OlympicsCities
    else -> DrawerAppScreen.MagnetoGame
}

// Passed the corresponding screen composable based on the current screen that's active.
@Composable
fun BodyContentComponent(
    ballGameViewModel: BallGameViewModel?,
    currentScreen: DrawerAppScreen,
    openDrawer: () -> Unit
) {
    when (currentScreen) {
        DrawerAppScreen.MagnetoGame -> MagnetoGame(
            openDrawer
        )
        DrawerAppScreen.PressureGame -> PressureGame(
            openDrawer
        )
        DrawerAppScreen.TicTacToe -> TicTacToe(
            openDrawer
        )
        DrawerAppScreen.BallGame -> ballGameViewModel?.let {
            BallGame(
                ballGameViewModel = it,
                openDrawer
            )
        }
        DrawerAppScreen.Statistics -> Statistics(
            openDrawer
        )
        DrawerAppScreen.OlympicsCities -> OlympicsCities(
            openDrawer
        )
    }
}

@Composable
fun MagnetoGame(openDrawer: () -> Unit) {
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
            color = Color(0xFFffd7d7),
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
fun PressureGame(openDrawer: () -> Unit) {
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
            color = Color(0xFFffe9d6),
            modifier = Modifier
                .weight(1f)
        ) {
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
fun TicTacToe(openDrawer: () -> Unit) {
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
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = 16.dp
        ) {
            Surface(
                modifier = Modifier.weight(1f)
            ) {
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
}

@Composable
fun BallGame(ballGameViewModel: BallGameViewModel, openDrawer: () -> Unit) {
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
        Surface(
            modifier = Modifier
                .weight(1f)
        ) {
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

@Composable
fun Statistics(openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(R.string.title5)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
        Surface(
            modifier = Modifier
                .weight(1f)
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(text = "Screen 5")
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OlympicsCities(openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(id = R.string.title6)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
        Surface(
            modifier = Modifier
                .weight(1f)
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    ShowMap(
                        mapViewModel = MapViewModel(),
                        context = LocalContext.current,
                        model = WikiViewModel()
                    )
                }
            )
        }
    }
}

enum class DrawerAppScreen {
    MagnetoGame,
    PressureGame,
    TicTacToe,
    BallGame,
    Statistics,
    OlympicsCities
}
