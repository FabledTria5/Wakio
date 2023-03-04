package dev.fabled.common.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val PrimaryLight = Color(0xFFC847F4)
val PrimaryDark = Color(0xFF6E54F7)
val PrimaryMuted = Color(0xFFB98FFE)
val DarkOrange = Color(0xFFF44747)
val LightOrange = Color(0xFFF7AC54)
val LightPink = Color(0xFFF147F4)
val LightRed = Color(0xFFF75468)
val BackgroundColor = Color(0xFF212327)

val TextFieldsColor = Color(0xFF393C41)

val PrimaryGradient = Brush.horizontalGradient(colors = listOf(PrimaryLight, PrimaryDark))
val PinkGradient = Brush.horizontalGradient(colors = listOf(LightPink, LightRed))
val OrangeGradient = Brush.horizontalGradient(colors = listOf(DarkOrange, LightOrange))