package com.dayooni.app.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dayooni.app.presentation.viewmodel.PersonDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailScreen(onBack: () -> Unit, onEdit: (Long) -> Unit, onAddDebt: (Long) -> Unit, onDebt: (Long, Long) -> Unit, viewModel: PersonDetailViewModel = hiltViewModel()) {
    val person by viewModel.person.collectAsStateWithLifecycle()
    val debts by viewModel.debts.collectAsStateWithLifecycle()
    Scaffold(topBar = { TopAppBar(title = { Text(person?.name ?: "Person") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.Edit, null) } }, actions = { IconButton(onClick = { onEdit(viewModel.personId) }) { Icon(Icons.Default.Edit, null) } }) }, floatingActionButton = { FloatingActionButton(onClick = { onAddDebt(viewModel.personId) }) { Icon(Icons.Default.Add, null) } }) { padding ->
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(padding)) {
            item { Text(person?.phone.orEmpty()) }
            item { Text(person?.notes.orEmpty()) }
            item { Text("Debts") }
            items(debts, key = { it.id }) { debt ->
                ListItem(headlineContent = { Text(debt.title) }, supportingContent = { Text("Remaining: %.2f".format(debt.amount - debt.paidAmount)) }, trailingContent = { Text(if (debt.isCreditor) "Receive" else "Owe") }, modifier = Modifier.clickable { onDebt(viewModel.personId, debt.id) })
            }
        }
    }
}
