package com.flowz.printfuljobtask.network

import com.flowz.printfuljobtask.models.Drinks
import retrofit2.http.GET

interface ApiServiceCalls {

    @GET("api/json/v1/1/search.php?s=margarita")
    suspend fun FetchMargaritaCocktails():Drinks
}