package com.jhiltunen.sensorlympics.rules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.sensorlympics.R

@Composable
fun MagnetoRules() {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false) }

            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(Icons.Sharp.HelpOutline, "", Modifier.size(25.dp))
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = stringResource(R.string.game_info))
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text(stringResource(R.string.ok_btn))
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun PressureRules() {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false) }

            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(Icons.Sharp.HelpOutline, stringResource(id = R.string.menu))
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = stringResource(id = R.string.game_info))
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text(stringResource(id = R.string.ok_btn))
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun TicTacToeRules() {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false) }

            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(Icons.Sharp.HelpOutline, stringResource(id = R.string.menu))
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = stringResource(id = R.string.game_info))
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text(stringResource(id = R.string.ok_btn))
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun BallGameRules() {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false) }

            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(Icons.Sharp.HelpOutline, stringResource(id = R.string.menu))
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = stringResource(id = R.string.game_info))
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text(stringResource(id = R.string.ok_btn))
                        }
                    },
                )
            }
        }
    }
}