package dev.fabled.common.code.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import dev.fabled.common.R

@Stable
sealed class Gender(val tag: String, @DrawableRes val icon: Int) {
    object Male : Gender(tag = "Male", icon = R.drawable.ic_male)
    object Female : Gender(tag = "Female", icon = R.drawable.ic_female)

    companion object {
        fun ofTag(tag: String): Gender = when(tag) {
            Male.tag -> Male
            Female.tag -> Female
            else -> throw IllegalArgumentException("There are only two genders")
        }
    }
}