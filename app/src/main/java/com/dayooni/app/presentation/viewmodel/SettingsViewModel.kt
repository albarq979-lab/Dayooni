package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.utils.JsonExporter
import com.dayooni.app.utils.PdfExporter
import com.dayooni.app.utils.SettingsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.net.Uri
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settings: SettingsStore, private val jsonExporter: JsonExporter, private val pdfExporter: PdfExporter) : ViewModel() {
    val darkMode = settings.darkMode.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
    val language = settings.language.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "system")
    val biometric = settings.biometricLock.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
    fun setDark(value: Boolean) = viewModelScope.launch { settings.setDarkMode(value) }
    fun setLanguage(value: String) = viewModelScope.launch { settings.setLanguage(value) }
    fun setBiometric(value: Boolean) = viewModelScope.launch { settings.setBiometricLock(value) }
    fun exportJson(uri: Uri) = viewModelScope.launch { jsonExporter.export(uri) }
    fun importJson(uri: Uri) = viewModelScope.launch { jsonExporter.import(uri) }
    fun exportPdf(uri: Uri) = viewModelScope.launch { pdfExporter.export(uri) }
}
