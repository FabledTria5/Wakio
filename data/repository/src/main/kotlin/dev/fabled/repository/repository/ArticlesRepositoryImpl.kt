package dev.fabled.repository.repository

import dev.fabled.domain.model.ArticleModel
import dev.fabled.domain.repository.ArticlesRepository
import dev.fabled.local.dao.ArticlesDao
import dev.fabled.remote.parser.FitnessApi
import dev.fabled.repository.mapper.toArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val fitnessApi: FitnessApi,
    private val articlesDao: ArticlesDao
) : ArticlesRepository {

    override fun getDailyArticle(): Flow<ArticleModel?> =
        articlesDao.getDailyArticle()
            .onEach { entity ->
                if (entity == null || entity.articleDate < LocalDate.now().toEpochDay())
                    fetchDailyArticle(entity?.articleDate)
            }
            .map { articleEntity ->
                articleEntity?.let { entity ->
                    ArticleModel(
                        url = entity.articleUrl,
                        imagePath = entity.articleImage,
                        articleName = entity.articleName,
                        shortText = entity.articleText
                    )
                }
            }

    private suspend fun fetchDailyArticle(savedArticleDate: Long?) {
        try {
            val articleEntity = fitnessApi.getDailyArticle().toArticleEntity()

            if (articleEntity.articleDate != savedArticleDate) {
                articlesDao.changeDailyArticle(articleEntity)
            } else {
                articlesDao.updateDailyArticleDate(articleEntity)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

}