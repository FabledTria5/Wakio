package dev.fabled.on_boarding.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
internal fun PagerIndicators(
    modifier: Modifier = Modifier,
    indicatorSize: Dp,
    pageCount: Int,
    currentPage: Int,
    activePageColor: Brush,
    inActivePageColor: Color
) {
    require(pageCount >= 0)

    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        for (page in 0 until pageCount) {
            Canvas(modifier = Modifier.size(indicatorSize)) {
                if (page == currentPage) {
                    drawCircle(brush = activePageColor)
                } else {
                    drawCircle(color = inActivePageColor)
                }
            }
            if (page < pageCount - 1)
                Spacer(modifier = Modifier.width(indicatorSize))
        }
    }
}