package dev.fabled.domain.model

data class ArticleModel(
    val url: String,
    val imagePath: String?,
    val articleName: String,
    val shortText: String
)
