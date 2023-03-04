package dev.fabled.remote.api

import dev.fabled.remote.dto.quotes.QuotesResponseItem
import retrofit2.http.GET

interface QuotesApi {

    @GET(value = "quotes")
    suspend fun getQuotes(): List<QuotesResponseItem>

}