package com.flowz.drinkcocktails.network

import com.flowz.drinkcocktails.models.Drinks
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceCalls {

    @GET("api/json/v1/1/search.php?")
    suspend fun FetchMargaritaCocktails(@Query("s") drinkType: String ):Drinks
}