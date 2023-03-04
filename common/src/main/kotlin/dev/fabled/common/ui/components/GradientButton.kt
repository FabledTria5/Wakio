package dev.fabled.common.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    gradient: Brush,
    shape: Shape,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color.White,
        disabledContentColor = Color.White.copy(alpha = .5f)
    ),
    content: @Composable RowScope.() -> Unit
) {
    val buttonAlpha by animateFloatAsState(
        targetValue = if (enabled) 1f else .5f
    )

    Button(
        modifier = modifier.background(brush = gradient, shape = shape, alpha = buttonAlpha),
        onClick = onClick,
        colors = colors,
        shape = shape,
        enabled = enabled,
        contentPadding = PaddingValues(),
    ) {
        content()
    }
}