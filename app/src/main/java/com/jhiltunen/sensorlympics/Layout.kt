package com.jhiltunen.sensorlympics

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.sensorlympics.ui.theme.Black
import com.jhiltunen.sensorlympics.ui.theme.RichBlack

@Composable
fun CardStyle(children: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        elevation = 16.dp
    ) {
        children()
    }
}

@Composable
fun SpaceBetweenColumn(children: @Composable () -> Unit) {
    Column (
        modifier = Modifier
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        children()
    }
}

@Composable
fun MenuModifier(children: @Composable () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        children()
    }
}

/*
@Composable
fun MainView() {
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "SensorLympics", fontSize = 40.sp, color = RichBlack)
        Image(
            painter = painterResource(R.drawable.rings),
            contentDescription = "Application logo",
            modifier = Modifier
                .clip(CutCornerShape(10.dp))
        )
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { navController.navigate("magnetogame") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = RichBlack),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = RichBlack)
            ) {
                Text(stringResource(R.string.game1))
            }
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedButton(
                onClick = { navController.navigate("pressuregame") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = RichBlack),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = RichBlack)
            ) {
                Text(stringResource(R.string.game2))
            }
            Spacer(modifier = Modifier.padding(12.dp))

            OutlinedButton(
                onClick = { navController.navigate("tictactoe") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = RichBlack),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = RichBlack)
            ) {
                Text(stringResource(R.string.game3))
            }
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedButton(
                onClick = { navController.navigate("") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = RichBlack),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = RichBlack)
            ) {
                Text(stringResource(id = R.string.game4))
            }
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedButton(
                onClick = { navController.navigate("olympicscities") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = RichBlack),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = RichBlack)
            ) {
                Text(text = "Olympic Cities")
            }
        }
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Text(text = "Statistics")
        }
    }
}
*/


