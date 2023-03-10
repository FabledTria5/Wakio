package dev.fabled.alarm.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import dev.fabled.alarm.R

@Stable
sealed class AlarmSoundModel(
    @RawRes val audio: Int,
    @StringRes val name: Int,
    val tag: String
) {

    object BakeKujira : AlarmSoundModel(
        audio = R.raw.bake_kujira,
        name = R.string.bake_kujira,
        tag = "bake_kujira"
    )

    object Bittersweet : AlarmSoundModel(
        audio = R.raw.bittersweet,
        name = R.string.bittersweet,
        tag = "bittersweet"
    )

    object FeelingAlive : AlarmSoundModel(
        audio = R.raw.feeling_alive,
        name = R.string.feeling_alive,
        tag = "feeling_alive"
    )

    object FloatingWorld : AlarmSoundModel(
        audio = R.raw.floating_world,
        name = R.string.floating_world,
        tag = "floating_world"
    )

    object FlowOfTime : AlarmSoundModel(
        audio = R.raw.flow_of_time,
        name = R.string.flow_of_time,
        tag = "flow_of_time"
    )

    object Hazakura : AlarmSoundModel(
        audio = R.raw.hazakura,
        name = R.string.hazakura,
        tag = "hazakura"
    )

    object Houou : AlarmSoundModel(
        audio = R.raw.houou,
        name = R.string.houou,
        tag = "houou"
    )

    object Ikigai : AlarmSoundModel(
        audio = R.raw.ikigai,
        name = R.string.ikigai,
        tag = "ikigai"
    )

    object Kinou : AlarmSoundModel(
        audio = R.raw.kinou,
        name = R.string.kinou,
        tag = "kinou"
    )

    object Kitsune : AlarmSoundModel(
        audio = R.raw.kitsune,
        name = R.string.kitsune,
        tag = "kitsune"
    )

    object Kogarashi : AlarmSoundModel(
        audio = R.raw.kogarashi,
        name = R.string.kogarashi,
        tag = "kogarashi"
    )

    object Komorebi : AlarmSoundModel(
        audio = R.raw.komorebi,
        name = R.string.komorebi,
        tag = "komorebi"
    )

    object Kouyou : AlarmSoundModel(
        audio = R.raw.kouyou,
        name = R.string.kouyou,
        tag = "kouyou"
    )

    object LotusBook : AlarmSoundModel(
        audio = R.raw.lotus_book,
        name = R.string.lotus_book,
        tag = "lotus_book"
    )

    object Natsukashi : AlarmSoundModel(
        audio = R.raw.natsukashi,
        name = R.string.natsukashi,
        tag = "natsukashi"
    )

    object Ookami : AlarmSoundModel(
        audio = R.raw.ookami,
        name = R.string.ookami,
        tag = "ookami"
    )

    object RedAutumn : AlarmSoundModel(
        audio = R.raw.red_autumn,
        name = R.string.red_autumn,
        tag = "red_autumn"
    )

    object Ukiyo : AlarmSoundModel(
        audio = R.raw.ukiyo,
        name = R.string.ukiyo,
        tag = "ukiyo"
    )

    companion object {

        fun getSoundsList() = listOf(
            BakeKujira,
            Bittersweet,
            FeelingAlive,
            FloatingWorld,
            FlowOfTime,
            Hazakura,
            Houou,
            Ikigai,
            Kinou,
            Kitsune,
            Kogarashi,
            Komorebi,
            Kouyou,
            LotusBook,
            Natsukashi,
            Ookami,
            RedAutumn,
            Ukiyo
        )

        fun getByTag(tag: String) = getSoundsList()
            .find { sound -> sound.tag == tag }
            ?: BakeKujira

        fun default() = BakeKujira

    }

}