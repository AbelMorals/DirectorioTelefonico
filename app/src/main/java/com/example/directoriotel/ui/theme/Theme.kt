package com.example.directoriotel.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ContactAppBluePrimaryDark,
    onPrimary = ContactAppOnPrimaryDark,
    primaryContainer = ContactAppPrimaryContainerDark,
    onPrimaryContainer = ContactAppOnPrimaryContainerDark,
    secondary = ContactAppBlueSecondaryDark,
    onSecondary = ContactAppOnSecondaryDark,
    secondaryContainer = ContactAppSecondaryContainerDark,
    onSecondaryContainer = ContactAppOnSecondaryContainerDark,
    tertiary = ContactAppTertiaryDark,
    onTertiary = ContactAppOnTertiaryDark,
    tertiaryContainer = ContactAppTertiaryContainerDark,
    onTertiaryContainer = ContactAppOnTertiaryContainerDark,
    error = ContactAppErrorDark,
    onError = ContactAppOnErrorDark,
    errorContainer = ContactAppErrorContainerDark,
    onErrorContainer = ContactAppOnErrorContainerDark,
    background = ContactAppBackgroundDark,
    onBackground = ContactAppOnBackgroundDark,
    surface = ContactAppSurfaceDark,
    onSurface = ContactAppOnSurfaceDark,
    surfaceVariant = ContactAppSurfaceVariantDark,
    onSurfaceVariant = ContactAppOnSurfaceVariantDark,
    outline = ContactAppOutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = ContactAppBluePrimaryLight,
    onPrimary = ContactAppOnPrimaryLight,
    primaryContainer = ContactAppPrimaryContainerLight,
    onPrimaryContainer = ContactAppOnPrimaryContainerLight,
    secondary = ContactAppBlueSecondaryLight,
    onSecondary = ContactAppOnSecondaryLight,
    secondaryContainer = ContactAppSecondaryContainerLight,
    onSecondaryContainer = ContactAppOnSecondaryContainerLight,
    tertiary = ContactAppTertiaryLight,
    onTertiary = ContactAppOnTertiaryLight,
    tertiaryContainer = ContactAppTertiaryContainerLight,
    onTertiaryContainer = ContactAppOnTertiaryContainerLight,
    error = ContactAppErrorLight,
    onError = ContactAppOnErrorLight,
    errorContainer = ContactAppErrorContainerLight,
    onErrorContainer = ContactAppOnErrorContainerLight,
    background = ContactAppBackgroundLight,
    onBackground = ContactAppOnBackgroundLight,
    surface = ContactAppSurfaceLight,
    onSurface = ContactAppOnSurfaceLight,
    surfaceVariant = ContactAppSurfaceVariantLight,
    onSurfaceVariant = ContactAppOnSurfaceVariantLight,
    outline = ContactAppOutlineLight
)

@Composable
fun DirectorioTelTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}