package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.data.DebtEntity
import com.dayooni.app.repository.DayooniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDebtViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val repo: DayooniRepository) : ViewModel() {
    val personId: Long = checkNotNull(savedStateHandle.get<String>("personId")).toLong()
    val debtId: Long? = savedStateHandle.get<String>("debtId")?.toLongOrNull()
    suspend fun load(): DebtEntity? = debtId?.let { repo.debtOnce(it) }
    fun save(title: String, amount: Double, isCreditor: Boolean, dueDate: Long?, note: String, onDone: () -> Unit) = viewModelScope.launch {
        if (title.isBlank() || amount <= 0.0) return@launch
        repo.saveDebt(DebtEntity(id = debtId ?: 0L, personId = personId, title = title.trim(), amount = amount, isCreditor = isCreditor, dueDate = dueDate, note = note.trim()))
        onDone()
    }
}
