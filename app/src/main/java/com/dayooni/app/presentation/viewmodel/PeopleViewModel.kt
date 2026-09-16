package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.data.PersonEntity
import com.dayooni.app.repository.DayooniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(private val repo: DayooniRepository) : ViewModel() {
    val people = repo.people().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    fun delete(person: PersonEntity) = viewModelScope.launch { repo.deletePerson(person) }
}
