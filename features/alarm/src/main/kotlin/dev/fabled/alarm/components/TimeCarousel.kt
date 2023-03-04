package dev.fabled.alarm.components

import androidx.compose.animation.Animatable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import dev.fabled.alarm.model.TimeModel
import dev.fabled.common.ui.theme.BackgroundColor
import kotlin.math.absoluteValue

@Composable
fun TimeCarousel(
    modifier: Modifier = Modifier,
    timeModel: TimeModel = TimeModel(),
    onTimeChanged: (TimeModel) -> Unit
) {
    val hours = (0..23).map { if (it.toString().length == 1) "0$it" else it.toString() }
    val minutes = (0..59).map { if (it.toString().length == 1) "0$it" else it.toString() }

    Row(
        modifier = modifier.drawWithContent {
            drawContent()
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        BackgroundColor,
                        Color.Transparent,
                        BackgroundColor
                    ),
                    start = Offset(x = size.width / 2, y = 0f),
                    end = Offset(x = size.width / 2, y = size.height)
                ),
                size = size
            )
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PickerCarousel(
            modifier = Modifier.height(250.dp),
            items = hours,
            initialItem = timeModel.hours,
            activeElementColor = Color.White,
            textSize = 56.sp,
            onItemChanged = {
                onTimeChanged(timeModel.copy(hours = it.toInt()))
            }
        )
        Text(
            modifier = Modifier.padding(horizontal = 15.dp),
            text = ":",
            color = Color.White,
            fontSize = 56.sp
        )
        PickerCarousel(
            modifier = Modifier.height(250.dp),
            items = minutes,
            initialItem = timeModel.minutes,
            activeElementColor = Color.White,
            textSize = 56.sp,
            onItemChanged = {
                onTimeChanged(timeModel.copy(minutes = it.toInt()))
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PickerCarousel(
    modifier: Modifier = Modifier,
    items: List<String>,
    initialItem: Int = 0,
    activeElementColor: Color,
    textSize: TextUnit,
    onItemChanged: (String) -> Unit
) {
    val pagerState = rememberPagerState(initialItem)
    val elementsColor = remember { Animatable(initialValue = activeElementColor) }

    LaunchedEffect(key1 = pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress) {
            elementsColor.animateTo(activeElementColor)
        } else {
            elementsColor.animateTo(Color.Gray)
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onItemChanged(items[it])
        }
    }

    VerticalPager(
        modifier = modifier,
        count = items.size,
        contentPadding = PaddingValues(vertical = 90.dp),
        state = pagerState
    ) {
        Text(
            modifier = Modifier.graphicsLayer {
                val pageOffset = calculateCurrentOffsetForPage(it).absoluteValue
                lerp(
                    start = .6f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },
            text = items[it],
            color = if (pagerState.currentPage == it) activeElementColor else elementsColor.value,
            fontSize = textSize
        )
    }
}