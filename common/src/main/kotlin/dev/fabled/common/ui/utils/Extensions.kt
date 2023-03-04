package dev.fabled.common.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * PI

        val x = cos(angleRad).toFloat()
        val y = sin(angleRad).toFloat()

        val radius = sqrt(size.width.pow(n = 2) + size.height.pow(n = 2)) / 2f
        val offset = center + Offset(x = x * radius, y =  y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)