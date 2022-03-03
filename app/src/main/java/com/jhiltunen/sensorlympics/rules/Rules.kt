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
import androidx.compose.ui.unit.dp

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
                        Text(text = "Game Info")
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Ok")
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
                Icon(Icons.Sharp.HelpOutline, "menu")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Dialog Title")
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Ok")
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
                Icon(Icons.Sharp.HelpOutline, "menu")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Dialog Title")
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Ok")
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
                Icon(Icons.Sharp.HelpOutline, "menu")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Dialog Title")
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Ok")
                        }
                    },
                )
            }
        }
    }
}