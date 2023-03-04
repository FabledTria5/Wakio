package dev.fabled.alarm.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
sealed class GradientUiModel(val colors: List<Color>, val tag: String) {

    object NearMoon : GradientUiModel(
        colors = listOf(Color(color = 0xFF5EE7DF), Color(color = 0xFFBC49CA)),
        tag = "near_moon"
    )

    object TeenNotebook : GradientUiModel(
        colors = listOf(Color(color = 0xFF9795F0), Color(color = 0xFFFbc8D4)),
        tag = "teen_note_book"
    )

    object NightParty : GradientUiModel(
        colors = listOf(Color(color = 0xFF0250C5), Color(color = 0xFFD43F8D)),
        tag = "night_party"
    )

    object OctoberSilence : GradientUiModel(
        colors = listOf(Color(color = 0xFFB721FF), Color(color = 0xFF21D4FD)),
        tag = "october_silence"
    )

    object FabledSunset : GradientUiModel(
        colors = listOf(
            Color(color = 0xFF231557),
            Color(color = 0xFF44107A),
            Color(color = 0xFFFF1361),
            Color(color = 0xFFFFF800)
        ),
        tag = "fabled_sunset"
    )

    object WideMatrix : GradientUiModel(
        colors = listOf(
            Color(color = 0xFFFCC5E4),
            Color(color = 0xFFFDA34B),
            Color(color = 0xFFFF7882),
            Color(color = 0xFFC8699E),
            Color(color = 0xFF7046AA),
            Color(color = 0xFF0C1DB8)
        ),
        tag = "wide_matrix"
    )

    companion object {
        private val gradientsList = listOf(
            NearMoon,
            TeenNotebook,
            NightParty,
            OctoberSilence,
            FabledSunset,
            WideMatrix
        )

        fun getRandom() = gradientsList.random()

        fun getByTag(tag: String) = gradientsList.find { it.tag == tag } ?: NearMoon
    }
}
