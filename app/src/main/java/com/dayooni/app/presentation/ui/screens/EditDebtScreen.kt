package com.dayooni.app.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dayooni.app.presentation.viewmodel.EditDebtViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDebtScreen(
    onBack: () -> Unit,
    onRecordPayment: (Long) -> Unit = {},
    viewModel: EditDebtViewModel = hiltViewModel()
) {
    var title by remember {
        mutableStateOf("")
    }

    var amount by remember {
        mutableStateOf("")
    }

    var note by remember {
        mutableStateOf("")
    }

    var isCreditor by remember {
        mutableStateOf(false)
    }

    var loaded by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (!loaded) {
            viewModel.load()?.let { debt ->
                title = debt.title
                amount = debt.amount.toString()
                note = debt.note
                isCreditor = debt.isCreditor
            }

            loaded = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (viewModel.debtId == null) {
                            "Add debt"
                        } else {
                            "Edit debt"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Title")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Amount")
                },
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButton(
                    selected = isCreditor,
                    onClick = {
                        isCreditor = true
                    }
                )

                Text(
                    text = "I receive",
                    modifier = Modifier.padding(top = 12.dp)
                )

                RadioButton(
                    selected = !isCreditor,
                    onClick = {
                        isCreditor = false
                    }
                )

                Text(
                    text = "I owe",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            OutlinedTextField(
                value = note,
                onValueChange = {
                    note = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Note")
                }
            )

            Button(
                onClick = {
                    scope.launch {
                        viewModel.save(
                            title,
                            amount.toDoubleOrNull() ?: 0.0,
                            isCreditor,
                            null,
                            note
                        ) {
                            onBack()
                        }
                    }
                },
                enabled = title.isNotBlank() &&
                    (amount.toDoubleOrNull() ?: 0.0) > 0.0
            ) {
                Text("Save")
            }

            if (viewModel.debtId != null) {
                Button(
                    onClick = {
                        viewModel.debtId?.let { debtId ->
                            onRecordPayment(debtId)
                        }
                    }
                ) {
                    Text("Record payment")
                }
            }
        }
    }
}