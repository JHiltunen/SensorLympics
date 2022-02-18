package com.jhiltunen.sensorlympics.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Grid() {

    val list = listOf("Magneto Game", "Pressure Game", "Tic-Tac-Toe Game", "Game 4")
    val context = LocalContext.current
    val navController = rememberNavController()


    Card(
        modifier = Modifier
            .padding(16.dp),
        elevation = 16.dp
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),

            // content padding
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(list.size) { index ->
                    Card(
                        backgroundColor = Color.Red,
                        modifier = Modifier
                            .clickable(
                                onClick = {  }
                                //onClick = { Toast.makeText(context, "Toast $index", Toast.LENGTH_LONG).show()}
                            )
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = 8.dp,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(16.dp),
                            text = list[index],
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun MainView() {
    SensorLympicsTheme {
        Grid()
    }
}
*/
