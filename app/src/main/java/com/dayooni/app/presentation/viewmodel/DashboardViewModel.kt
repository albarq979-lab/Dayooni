package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.repository.DayooniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(repo: DayooniRepository) : ViewModel() {
    val people = repo.people().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val debts = repo.debts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val totalOwed = repo.totalOwed().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)
    val totalToReceive = repo.totalToReceive().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)
}
