package dev.fabled.domain.repository

import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    fun getQuoteOfTheDay(): Flow<String>

}