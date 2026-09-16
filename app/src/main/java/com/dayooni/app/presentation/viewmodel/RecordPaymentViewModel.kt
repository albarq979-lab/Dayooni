package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.repository.DayooniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordPaymentViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val repo: DayooniRepository) : ViewModel() {
    val debtId: Long = checkNotNull(savedStateHandle.get<String>("debtId")).toLong()
    fun save(amount: Double, note: String, onDone: () -> Unit) = viewModelScope.launch {
        if (amount <= 0.0) return@launch
        repo.debtOnce(debtId)?.let { debt -> repo.addPayment(debt, amount, note.trim()); onDone() }
    }
}
