package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.data.NoteEntity
import com.dayooni.app.repository.DayooniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repo: DayooniRepository) : ViewModel() {
    val notes = repo.notes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    fun add(title: String, content: String) = viewModelScope.launch { if (title.isNotBlank() && content.isNotBlank()) repo.saveNote(NoteEntity(title = title.trim(), content = content.trim())) }
    fun delete(note: NoteEntity) = viewModelScope.launch { repo.deleteNote(note) }
}
