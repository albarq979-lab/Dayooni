package com.dayooni.app.presentation.ui.screens

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dayooni.app.presentation.viewmodel.ReportsViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(onBack: () -> Unit, viewModel: ReportsViewModel = hiltViewModel()) {
    val data by viewModel.data.collectAsStateWithLifecycle()
    Scaffold(topBar = { TopAppBar(title = { Text("Reports") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Outstanding balances", modifier = Modifier.padding(bottom = 12.dp))
            AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
                BarChart(context).apply { description.isEnabled = false; axisRight.isEnabled = false; legend.isEnabled = false }
            }, update = { chart ->
                val entries = listOf(BarEntry(0f, data.first.toFloat()), BarEntry(1f, data.second.toFloat()))
                val set = BarDataSet(entries, "Balances")
                set.color = Color.rgb(49, 95, 114)
                chart.data = BarData(set)
                chart.invalidate()
            })
        }
    }
}
