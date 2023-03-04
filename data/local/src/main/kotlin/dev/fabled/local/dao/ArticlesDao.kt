package dev.fabled.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.fabled.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert
    suspend fun insertArticle(articleEntity: ArticleEntity)

    @Update
    suspend fun updateDailyArticleDate(articleEntity: ArticleEntity)

    @Query(value = "SELECT * FROM article_table LIMIT 1")
    fun getDailyArticle(): Flow<ArticleEntity?>

    @Transaction
    suspend fun changeDailyArticle(articleEntity: ArticleEntity) {
        clearArticlesTable()
        insertArticle(articleEntity)
    }

    @Query(value = "DELETE FROM article_table")
    suspend fun clearArticlesTable()

}