package dev.fabled.remote.dto.articles

import androidx.annotation.Keep

@Keep
data class ArticleDto(
    val url: String,
    val imagePath: String,
    val articleName: String,
    val shortText: String,
    val articleDate: Long
)
