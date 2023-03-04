package dev.fabled.home.model

import androidx.compose.runtime.Stable

@Stable
data class UiArticle(
    val url: String,
    val imagePath: String?,
    val articleTitle: String,
    val articleText: String
)
