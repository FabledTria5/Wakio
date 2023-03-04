package dev.fabled.authorization.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import dev.fabled.authorization.R

@Stable
sealed class ProfileBackgroundModel(
    @DrawableRes val backgroundPicture: Int,
    @StringRes val imageName: Int,
    val tag: String
) {

    object BeforeSunrise : ProfileBackgroundModel(
        backgroundPicture = R.drawable.before_sunrise,
        imageName = R.string.before_sunrise,
        tag = "before_sunrise"
    )

    object Sunrise : ProfileBackgroundModel(
        backgroundPicture = R.drawable.sunrise,
        imageName = R.string.sunrising,
        tag = "sunrise"
    )

    object Sunset : ProfileBackgroundModel(
        backgroundPicture = R.drawable.sunset,
        imageName = R.string.sunset,
        tag = "sunset"
    )

    object AfterSunrise : ProfileBackgroundModel(
        backgroundPicture = R.drawable.after_sunrise,
        imageName = R.string.after_sunrise,
        tag = "after_sunrise"
    )

    companion object {
        fun getPictures() = listOf(BeforeSunrise, Sunrise, Sunset, AfterSunrise)

        fun getByTag(tag: String) = when (tag) {
            BeforeSunrise.tag -> BeforeSunrise
            Sunrise.tag -> Sunrise
            Sunset.tag -> Sunset
            AfterSunrise.tag -> AfterSunrise
            else -> throw IllegalArgumentException("This tag is not exists!")
        }
    }

}
