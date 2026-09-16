package com.dayooni.app.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF315F72),
    secondary = Color(0xFF4F626B),
    tertiary = Color(0xFF635F78)
)
private val DarkColors = darkColorScheme(
    primary = Color(0xFF9DCCDF),
    secondary = Color(0xFFB5CAD2),
    tertiary = Color(0xFFC9C2E0)
)

@Composable
fun DayooniTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColors else LightColors, content = content)
}
