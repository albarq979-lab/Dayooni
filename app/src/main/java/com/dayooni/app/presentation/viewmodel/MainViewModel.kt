package com.dayooni.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayooni.app.utils.SettingsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val settings: SettingsStore) : ViewModel() {
    val onboarded = settings.onboarded.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
    val biometricLock = settings.biometricLock.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
    val darkMode = settings.darkMode.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
    val language = settings.language.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "system")
    fun completeOnboarding() = viewModelScope.launch { settings.setOnboarded(true) }
}
