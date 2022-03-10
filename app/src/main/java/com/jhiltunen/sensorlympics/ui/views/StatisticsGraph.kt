package com.jhiltunen.sensorlympics.ui.views

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.jhiltunen.sensorlympics.MainActivity.Companion.scoreViewModel
import com.jhiltunen.sensorlympics.R
import com.jhiltunen.sensorlympics.ui.layouts.CardStyle
import com.jhiltunen.sensorlympics.ui.theme.Ivory
import com.jhiltunen.sensorlympics.ui.theme.YellowRed

@ExperimentalFoundationApi
@Composable
fun GraphView() {
    val pressureList by scoreViewModel.getGameScore("Pressure").observeAsState()
    val magnetoList by scoreViewModel.getGameScore("Magneto").observeAsState()
    val ballList by scoreViewModel.getGameScore("Ball").observeAsState()
    val ticList by scoreViewModel.getGameScore("TicTac").observeAsState()

    val pressureScoreList by scoreViewModel.getGameScore("Pressure").observeAsState()
    val magnetoScoreList by scoreViewModel.getGameScore("Magneto").observeAsState()
    val pressureHighScore by scoreViewModel.getHighscore("Pressure").observeAsState()
    val magnetoHighScore by scoreViewModel.getHighscore("Magneto").observeAsState()

    val ballScoreList by scoreViewModel.getGameScore("Ball").observeAsState()
    val ticScoreList by scoreViewModel.getGameScore("TicTac").observeAsState()
    val ballHighScore by scoreViewModel.getHighscore("Ball").observeAsState()
    val ticHighScore by scoreViewModel.getHighscore("TicTac").observeAsState()

    val pressureAvgScore by scoreViewModel.getAvgScore("Pressure").observeAsState()
    val magnetoAvgScore by scoreViewModel.getAvgScore("Magneto").observeAsState()
    val ballAvgScore by scoreViewModel.getAvgScore("Ball").observeAsState()
    val ticAvgScore by scoreViewModel.getAvgScore("TicTac").observeAsState()


    val entriesGame1: MutableList<Entry> = ArrayList()
    val entriesGame2: MutableList<Entry> = ArrayList()
    val entriesGame3: MutableList<Entry> = ArrayList()
    val entriesGame4: MutableList<Entry> = ArrayList()

    var graphOrStats by remember { mutableStateOf(false) }

    CardStyle {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Button(
                onClick = {
                    graphOrStats = !graphOrStats
                },
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                if (!graphOrStats) {
                    Text(text = stringResource(id = R.string.graph_stats))
                } else {
                    Text(text = stringResource(id = R.string.graph_graph))
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))

            if (!graphOrStats) {
                Column {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                stringResource(id = R.string.magneto_game),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text("Games played: ${magnetoScoreList?.size ?: 0}")
                            Text("High score: ${magnetoHighScore ?: 0}")
                            Text("Average score:  ${magnetoAvgScore ?: 0}")

                        }
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                stringResource(id = R.string.pressure_game),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text("Games played: ${pressureScoreList?.size}")
                            Text("High score: ${pressureHighScore ?: 0}")
                            Text("Average score:  ${pressureAvgScore ?: 0}")

                        }
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                stringResource(id = R.string.ball_game),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text("Games played: ${ballList?.size ?: 0}")
                            Text("High score: ${ballHighScore ?: 0}")
                            Text("Average score:  ${ballAvgScore ?: 0}")

                        }
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                stringResource(id = R.string.tictactoe_game),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text("Games played: ${ticList?.size ?: 0}")
                            Text("High score: ${ticHighScore ?: 0}")
                            Text("Average score:  ${ticAvgScore ?: 0}")

                        }
                    }
                }

            } else {
                if (magnetoList != null) {
                    magnetoList?.forEachIndexed { index, bpm ->
                        entriesGame1.add(Entry(index.toFloat(), bpm.toFloat()))
                    }
                }
                if (pressureList != null) {
                    pressureList?.forEachIndexed { index, bpm ->
                        entriesGame2.add(Entry(index.toFloat(), bpm.toFloat()))
                    }
                }



                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Ivory)
                        .padding(16.dp),
                    factory = { context: Context ->
                        val view = LineChart(context)
                        view.legend.isEnabled = true

                        val setGame1 =
                            LineDataSet(entriesGame1, context.getString(R.string.graph_magneto))
                        setGame1.axisDependency = AxisDependency.LEFT
                        val setGame2 =
                            LineDataSet(entriesGame2, context.getString(R.string.graph_pressure))
                        setGame2.axisDependency = AxisDependency.LEFT
                        val setGame3 =
                            LineDataSet(entriesGame3, context.getString(R.string.graph_ball))
                        setGame3.axisDependency = AxisDependency.LEFT
                        val setGame4 =
                            LineDataSet(entriesGame4, context.getString(R.string.graph_tic_tac_toe))
                        setGame4.axisDependency = AxisDependency.LEFT

                        setGame1.color = context.getColor(R.color.olympic_blue)
                        setGame1.setCircleColor(context.getColor(R.color.olympic_blue))
                        setGame1.lineWidth = 3f

                        setGame2.color = context.getColor(R.color.olympic_yellow)
                        setGame2.setCircleColor(context.getColor(R.color.olympic_yellow))
                        setGame2.lineWidth = 2f

                        setGame3.color = context.getColor(R.color.black)
                        setGame3.setCircleColor(context.getColor(R.color.black))
                        setGame1.lineWidth = 3f

                        setGame4.color = context.getColor(R.color.olympic_green)
                        setGame4.setCircleColor(context.getColor(R.color.olympic_green))
                        setGame1.lineWidth = 3f

                        val dataSets: MutableList<ILineDataSet> = ArrayList()
                        dataSets.add(setGame1)
                        dataSets.add(setGame2)
                        dataSets.add(setGame3)
                        dataSets.add(setGame4)

                        val data = LineData(dataSets)

                        //val data = LineData(LineDataSet(entries, context.getString(R.string.game_points_graph)))
                        //val data2 = LineData(LineDataSet(entries2, context.getString(R.string.game_points_graph)))
                        val desc = Description()
                        desc.text = context.getString(R.string.score_graph)
                        view.description = desc
                        view.data = data
                        view // return the view
                    },
                    update = { view ->
                        // Update the view
                        view.invalidate()
                    }
                )
            }
        }
    }
}