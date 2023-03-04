package dev.fabled.repository.mapper

import dev.fabled.local.entities.ArticleEntity
import dev.fabled.remote.dto.articles.ArticleDto

fun ArticleDto.toArticleEntity(): ArticleEntity = ArticleEntity(
    articleName = articleName,
    articleImage = imagePath,
    articleText = shortText,
    articleUrl = url,
    articleDate = articleDate
)