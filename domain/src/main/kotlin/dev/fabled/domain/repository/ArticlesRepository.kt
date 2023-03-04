package dev.fabled.domain.repository

import dev.fabled.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    fun getDailyArticle(): Flow<ArticleModel?>
}