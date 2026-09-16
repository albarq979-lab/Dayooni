package com.dayooni.app.presentation.ui.screens

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity

@Composable
fun LockScreen(onUnlocked: () -> Unit) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("App locked")
        Button(onClick = { authenticate(context, onUnlocked) }) { Text("Unlock with fingerprint") }
    }
    LaunchedEffect(Unit) { authenticate(context, onUnlocked) }
}

private fun authenticate(context: Context, onUnlocked: () -> Unit) {
    val activity = context as? FragmentActivity ?: return
    val manager = BiometricManager.from(activity)
    if (manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) != BiometricManager.BIOMETRIC_SUCCESS) return
    val executor = activity.mainExecutor
    val prompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) { onUnlocked() }
    })
    val info = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Dayooni")
        .setSubtitle("Unlock your debt manager")
        .setNegativeButtonText("Cancel")
        .build()
    prompt.authenticate(info)
}
