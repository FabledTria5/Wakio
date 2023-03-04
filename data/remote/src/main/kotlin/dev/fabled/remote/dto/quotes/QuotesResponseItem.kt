package dev.fabled.remote.dto.quotes

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class QuotesResponseItem(
    @SerialName("author")
    val author: String?,
    @SerialName("text")
    val text: String
)