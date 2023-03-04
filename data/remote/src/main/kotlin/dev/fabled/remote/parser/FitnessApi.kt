package dev.fabled.remote.parser

import dev.fabled.remote.dto.articles.ArticleDto

interface FitnessApi {

    suspend fun getDailyArticle(): ArticleDto

}