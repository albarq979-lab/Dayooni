package com.dayooni.app.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dayooni.app.presentation.ui.components.AmountCard
import com.dayooni.app.presentation.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onPeople: () -> Unit, onReports: () -> Unit, onSettings: () -> Unit, onNotes: () -> Unit, onAddPerson: () -> Unit, viewModel: DashboardViewModel = hiltViewModel()) {
    val people by viewModel.people.collectAsStateWithLifecycle()
    val owed by viewModel.totalOwed.collectAsStateWithLifecycle()
    val receive by viewModel.totalToReceive.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        androidx.compose.material3.TopAppBar(title = { Text("Dayooni") }, actions = {
            IconButton(onClick = onPeople) { Icon(Icons.Default.People, null) }
            IconButton(onClick = onReports) { Icon(Icons.Default.ShowChart, null) }
            IconButton(onClick = onNotes) { Icon(Icons.Default.Notes, null) }
            IconButton(onClick = onSettings) { Icon(Icons.Default.Settings, null) }
        })
    }, floatingActionButton = { FloatingActionButton(onClick = onAddPerson) { Icon(Icons.Default.Add, null) } }) { padding ->
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(padding)) {
            item { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) { AmountCard("I owe", owed, Modifier.weight(1f)); AmountCard("To receive", receive, Modifier.weight(1f)) } }
            item { Text("People (${people.size})", style = MaterialTheme.typography.titleLarge) }
            if (people.isEmpty()) item { Card { Column(Modifier.padding(20.dp)) { Text("No people yet. Tap + to add one.") } } }
            people.take(5).forEach { person -> item { Text(person.name, style = MaterialTheme.typography.bodyLarge) } }
        }
    }
}
