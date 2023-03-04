package dev.fabled.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.fabled.local.entities.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Insert
    suspend fun insertQuotes(entity: QuoteEntity)

    @Transaction
    suspend fun updateQuoteOfTheDay(quoteEntity: QuoteEntity) {
        clearQuotesTable()
        insertQuotes(quoteEntity)
    }

    @Query(value = "SELECT * FROM quotes_table LIMIT 1")
    fun getRandomQuote(): Flow<QuoteEntity?>

    @Query(value = "DELETE FROM quotes_table")
    suspend fun clearQuotesTable()

}