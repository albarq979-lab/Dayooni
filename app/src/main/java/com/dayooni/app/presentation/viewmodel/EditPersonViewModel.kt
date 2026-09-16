package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.data.PersonEntity
import com.dayooni.app.repository.DayooniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPersonViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle, private val repo: DayooniRepository) : ViewModel() {
    val personId: Long? = savedStateHandle.get<String>("personId")?.toLongOrNull()
    suspend fun load(): PersonEntity? = personId?.let { repo.personOnce(it) }
    fun save(name: String, phone: String, notes: String, onDone: () -> Unit) = viewModelScope.launch {
        if (name.isBlank()) return@launch
        repo.savePerson(PersonEntity(id = personId ?: 0L, name = name.trim(), phone = phone.trim(), notes = notes.trim()))
        onDone()
    }
}
