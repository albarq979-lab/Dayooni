package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.data.DebtEntity
import com.dayooni.app.repository.DayooniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val repo: DayooniRepository) : ViewModel() {
    val personId: Long = checkNotNull(savedStateHandle.get<String>("personId")).toLong()
    val person = repo.person(personId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val debts = repo.debtsForPerson(personId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    fun deleteDebt(debt: DebtEntity) = viewModelScope.launch { repo.deleteDebt(debt) }
}
