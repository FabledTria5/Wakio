package dev.fabled.domain.use_cases.articles

import dev.fabled.domain.model.ArticleModel
import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDailyArticle @Inject constructor(private val articlesRepository: ArticlesRepository) {

    operator fun invoke(): Flow<Resource<ArticleModel>> = articlesRepository.getDailyArticle()
        .filterNotNull()
        .map { model ->
            Resource.Success(data = model)
        }

}