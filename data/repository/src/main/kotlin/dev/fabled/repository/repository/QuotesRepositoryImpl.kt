package dev.fabled.repository.repository

import dev.fabled.domain.repository.QuotesRepository
import dev.fabled.local.dao.QuotesDao
import dev.fabled.local.entities.QuoteEntity
import dev.fabled.remote.api.QuotesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val quotesApi: QuotesApi,
    private val quotesDao: QuotesDao,
) : QuotesRepository {

    override fun getQuoteOfTheDay(): Flow<String> = quotesDao.getRandomQuote()
        .onEach { quote ->
            if (quote == null) {
                getQuotesList()
                return@onEach
            }

            val epochDay = LocalDate.now().toEpochDay()
            if (quote.createdAt < epochDay) {
                getQuotesList()
            }
        }
        .filterNotNull()
        .map { quote -> quote.quoteText }

    private suspend fun getQuotesList() {
        val randomQuote = quotesApi.getQuotes().random()
        val quoteEntity =
            QuoteEntity(quoteText = randomQuote.text, createdAt = LocalDate.now().toEpochDay())

        quotesDao.updateQuoteOfTheDay(quoteEntity)
    }

}