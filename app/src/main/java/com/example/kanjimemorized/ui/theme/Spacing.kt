package com.example.kanjimemorized.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

data class Spacing(
    val default: Dp = SpacingTokens.default,
    val extraSmall: Dp = SpacingTokens.extraSmall,
    val small: Dp = SpacingTokens.small,
    val medium: Dp = SpacingTokens.medium,
    val large: Dp = SpacingTokens.large,
    val extraLarge: Dp = SpacingTokens.extraLarge,
)

internal val LocalSpacing: ProvidableCompositionLocal<Spacing> = staticCompositionLocalOf { Spacing() }

internal object SpacingTokens {
    val default: Dp = 0.dp
    val extraSmall: Dp = 4.dp
    val small: Dp = 8.dp
    val medium: Dp = 16.dp
    val large: Dp = 32.dp
    val extraLarge: Dp = 64.dp
}