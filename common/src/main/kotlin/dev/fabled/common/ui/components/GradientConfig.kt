package dev.fabled.common.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun GradientIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String?,
    brush: Brush
) {
    Icon(
        modifier = modifier
            .graphicsLayer(alpha = .99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(brush = brush, blendMode = BlendMode.SrcAtop)
                }
            },
        imageVector = imageVector,
        contentDescription = contentDescription
    )
}