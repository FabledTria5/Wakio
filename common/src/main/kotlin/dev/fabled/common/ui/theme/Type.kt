package dev.fabled.common.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.fabled.common.R

val Oxygen = FontFamily(
    fonts = listOf(
        Font(resId = R.font.oxygen_light, weight = FontWeight.Light),
        Font(resId = R.font.oxygen),
        Font(resId = R.font.oxygen_bold, weight = FontWeight.Bold)
    )
)

val Roboto = FontFamily(
    fonts = listOf(
        Font(resId = R.font.roboto_thin, weight = FontWeight.Thin),
        Font(resId = R.font.roboto, weight = FontWeight.Normal)
    )
)