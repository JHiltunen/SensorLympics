package com.jhiltunen.sensorlympics.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    Column(
        modifier = Modifier
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        children()
    }
}

