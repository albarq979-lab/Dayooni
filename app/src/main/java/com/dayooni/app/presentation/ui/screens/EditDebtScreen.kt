package com.dayooni.app.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dayooni.app.presentation.viewmodel.EditDebtViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDebtScreen(onBack: () -> Unit, onRecordPayment: (Long) -> Unit = {}, viewModel: EditDebtViewModel = hiltViewModel()) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var isCreditor by remember { mutableStateOf(false) }
    var loaded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        if (!loaded) { viewModel.load()?.let { title = it.title; amount = it.amount.toString(); note = it.note; isCreditor = it.isCreditor }; loaded = true }
    }
    Scaffold(topBar = { TopAppBar(title = { Text(if (viewModel.debtId == null) "Add debt" else "Edit debt") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(title, { title = it }, Modifier.fillMaxWidth(), label = { Text("Title") }, singleLine = true)
            OutlinedTextField(amount, { amount = it }, Modifier.fillMaxWidth(), label = { Text("Amount") }, singleLine = true)
            Row { RadioButton(isCreditor, { isCreditor = true }); Text("I receive", modifier = Modifier.padding(top = 12.dp)); RadioButton(!isCreditor, { isCreditor = false }); Text("I owe", modifier = Modifier.padding(top = 12.dp)) }
            OutlinedTextField(note, { note = it }, Modifier.fillMaxWidth(), label = { Text("Note") })
            Button(onClick = { scope.launch { viewModel.save(title, amount.toDoubleOrNull() ?: 0.0, isCreditor, null, note) { onBack() } } }, enabled = title.isNotBlank() && (amount.toDoubleOrNull() ?: 0.0) > 0.0) { Text("Save") }
            if (viewModel.debtId != null) Button(onClick = { viewModel.debtId?.let(onRecordPayment) }) { Text("Record payment") }
        }
    }
}
