package com.flowz.printfuljobtask.drinksrepository

import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.network.DrinksRetrieverApiClient
//import com.flowz.printfuljobtask.roomdb.DrinksDao
//import com.flowz.printfuljobtask.roomdb.DrinksDatabase

class DrinksRepository (val apiClient: DrinksRetrieverApiClient){

    suspend fun fetchAllDrinks():Drinks{
        return apiClient.service.FetchMargaritaCocktails()

    }

}