package com.jhiltunen.sensorlympics.ui.views

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.jhiltunen.sensorlympics.CardStyle
import com.jhiltunen.sensorlympics.R


@Composable
fun GraphView() {


    val bpmList = listOf(1,2,3,4,5,6,6)
    val bpmList2 = listOf(11,22,33,44,55,66,68)

    val entriesGame1: MutableList<Entry> = ArrayList()
    val entriesGame2: MutableList<Entry> = ArrayList()


    bpmList?.forEachIndexed { index, bpm ->
        entriesGame1.add(Entry(index.toFloat(), bpm.toFloat()))
    }
    bpmList2?.forEachIndexed { index, bpm ->
        entriesGame2.add(Entry(index.toFloat(), bpm.toFloat()))
    }

    val setGame1 = LineDataSet(entriesGame1, stringResource(R.string.graph_magneto))
    setGame1.axisDependency = AxisDependency.LEFT
    val setGame2 = LineDataSet(entriesGame2, stringResource(R.string.graph_pressure))
    setGame2.axisDependency = AxisDependency.LEFT

    val dataSets: MutableList<ILineDataSet> = ArrayList()
    dataSets.add(setGame1)
    dataSets.add(setGame2)

    val data = LineData(dataSets)



    CardStyle {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        factory = { context: Context ->
            val view = LineChart(context)
            view.legend.isEnabled = true
            //val data = LineData(LineDataSet(entries, context.getString(R.string.game_points_graph)))
            //val data2 = LineData(LineDataSet(entries2, context.getString(R.string.game_points_graph)))
            val desc = Description()
            desc.text = context.getString(R.string.score_graph)
            view.description = desc;
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