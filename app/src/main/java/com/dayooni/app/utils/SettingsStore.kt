package com.dayooni.app.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore("settings")

@Singleton
class SettingsStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val darkKey = booleanPreferencesKey("dark_mode")
    private val languageKey = stringPreferencesKey("language")
    private val biometricKey = booleanPreferencesKey("biometric_lock")
    private val onboardedKey = booleanPreferencesKey("onboarded")

    val darkMode: Flow<Boolean> = context.settingsDataStore.data.map { it[darkKey] ?: false }
    val language: Flow<String> = context.settingsDataStore.data.map { it[languageKey] ?: "system" }
    val biometricLock: Flow<Boolean> = context.settingsDataStore.data.map { it[biometricKey] ?: false }
    val onboarded: Flow<Boolean> = context.settingsDataStore.data.map { it[onboardedKey] ?: false }

    suspend fun setDarkMode(value: Boolean) = context.settingsDataStore.edit { it[darkKey] = value }
    suspend fun setLanguage(value: String) = context.settingsDataStore.edit { it[languageKey] = value }
    suspend fun setBiometricLock(value: Boolean) = context.settingsDataStore.edit { it[biometricKey] = value }
    suspend fun setOnboarded(value: Boolean) = context.settingsDataStore.edit { it[onboardedKey] = value }
}
