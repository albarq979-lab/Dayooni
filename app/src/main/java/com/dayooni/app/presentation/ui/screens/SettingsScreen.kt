package com.dayooni.app.presentation.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dayooni.app.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(onBack: () -> Unit, onReminders: () -> Unit, viewModel: SettingsViewModel = hiltViewModel()) {
    val dark by viewModel.darkMode.collectAsStateWithLifecycle()
    val biometric by viewModel.biometric.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    val exportJson = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri -> uri?.let(viewModel::exportJson) }
    val importJson = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> uri?.let(viewModel::importJson) }
    val exportPdf = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri -> uri?.let(viewModel::exportPdf) }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Dark mode"); Switch(checked = dark, onCheckedChange = { viewModel.setDark(it) }) }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Biometric lock"); Switch(checked = biometric, onCheckedChange = { viewModel.setBiometric(it) }) }
        Text("Language: $language")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { Button(onClick = { viewModel.setLanguage("system") }) { Text("System") }; Button(onClick = { viewModel.setLanguage("en") }) { Text("English") }; Button(onClick = { viewModel.setLanguage("ar") }) { Text("العربية") } }
        Button(onClick = onReminders, modifier = Modifier.fillMaxWidth()) { Text("Reminders") }
        Button(onClick = { exportJson.launch("dayooni-backup.json") }, modifier = Modifier.fillMaxWidth()) { Text("Export JSON") }
        Button(onClick = { importJson.launch(arrayOf("application/json", "text/json", "text/plain")) }, modifier = Modifier.fillMaxWidth()) { Text("Import JSON") }
        Button(onClick = { exportPdf.launch("dayooni-report.pdf") }, modifier = Modifier.fillMaxWidth()) { Text("Export PDF") }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
    }
}
