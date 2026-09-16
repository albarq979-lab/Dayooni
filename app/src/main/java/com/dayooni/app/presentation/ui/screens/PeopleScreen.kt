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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.dayooni.app.data.PersonEntity
import com.dayooni.app.presentation.viewmodel.PeopleViewModel
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreen(onBack: () -> Unit, onAdd: () -> Unit, onPerson: (Long) -> Unit, viewModel: PeopleViewModel = hiltViewModel()) {
    val people by viewModel.people.collectAsStateWithLifecycle()
    Scaffold(topBar = { TopAppBar(title = { Text("People") }, navigationIcon = { androidx.compose.material3.IconButton(onClick = onBack) { Icon(Icons.Default.Add, null) } }) }, floatingActionButton = { FloatingActionButton(onClick = onAdd) { Icon(Icons.Default.Add, null) } }) { padding ->
        if (people.isEmpty()) Column(Modifier.fillMaxSize().padding(padding), verticalArrangement = Arrangement.Center) { Text("No people", modifier = Modifier.padding(24.dp)) }
        else LazyColumn(contentPadding = PaddingValues(8.dp), modifier = Modifier.padding(padding)) { items(people, key = { it.id }) { person: PersonEntity -> ListItem(headlineContent = { Text(person.name) }, supportingContent = { Text(person.phone) }, modifier = Modifier.padding(vertical = 2.dp).clickable { onPerson(person.id) }) } }
    }
}
