package com.jhiltunen.sensorlympics.menu

import androidx.annotation.StringRes
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
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.api.MapViewModel
import com.jhiltunen.sensorlympics.api.WeatherViewModel
import com.jhiltunen.sensorlympics.ui.views.*
import com.jhiltunen.sensorlympics.viewmodels.TicTacToeViewModel
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun Menu(screen: DrawerAppScreen) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val currentScreen = remember { mutableStateOf(screen) }
    val coroutineScope = rememberCoroutineScope()


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
                currentScreen = currentScreen.value,
                openDrawer = {
                    coroutineScope.launch { drawerState.open() }

                }
            )
        }
    )
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
                    Text(
                        text = stringResource(id = screen.nameRes),
                        modifier = Modifier.padding(16.dp)
                    )
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
    4 -> DrawerAppScreen.OlympicsCities
    5 -> DrawerAppScreen.Statistics
    else -> DrawerAppScreen.MagnetoGame
}

// Passed the corresponding screen composable based on the current screen that's active.
@ExperimentalFoundationApi
@Composable
fun BodyContentComponent(
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
        DrawerAppScreen.BallGame -> BallGame(
            openDrawer
        )

        DrawerAppScreen.Statistics -> Statistics(
            openDrawer
        )
        DrawerAppScreen.OlympicsCities -> OlympicsCities(
            openDrawer
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun MagnetoGame(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(R.string.magneto_game)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.menu)
                    )
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

@ExperimentalFoundationApi
@Composable
fun PressureGame(openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(R.string.pressure_game)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.menu)
                    )
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
            title = { Text(stringResource(R.string.tictactoe_game)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.menu)
                    )
                }
            }
        )

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

@ExperimentalFoundationApi
@Composable
fun BallGame(openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(R.string.ball_game)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.menu)
                    )
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
                    BallGameView()
                }
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun OlympicsCities(openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(id = R.string.olympic_cities)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.menu)
                    )
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
                        model = WeatherViewModel()
                    )
                }
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Statistics(openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { Text(stringResource(R.string.statistics)) },
            navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.menu)
                    )
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
                    GraphView()
                }
            )
        }
    }
}

enum class DrawerAppScreen(@StringRes val nameRes: Int) {
    MagnetoGame(R.string.magneto_game),
    PressureGame(R.string.pressure_game),
    TicTacToe(R.string.tictactoe_game),
    BallGame(R.string.ball_game),
    OlympicsCities(R.string.olympic_cities),
    Statistics(R.string.statistics)
}
