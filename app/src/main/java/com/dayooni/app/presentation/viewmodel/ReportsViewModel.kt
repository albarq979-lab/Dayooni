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
class ReportsViewModel @Inject constructor(repo: DayooniRepository) : ViewModel() {
    val data = combine(repo.totalOwed(), repo.totalToReceive()) { owed, receive -> Pair(owed, receive) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Pair(0.0, 0.0))
}
