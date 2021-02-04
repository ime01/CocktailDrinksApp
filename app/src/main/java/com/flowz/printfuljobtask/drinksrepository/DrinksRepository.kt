package com.flowz.printfuljobtask.drinksrepository

import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.network.DrinksRetrieverApiClient
import com.flowz.printfuljobtask.roomdb.DrinksDao
import com.flowz.printfuljobtask.roomdb.DrinksDatabase

class DrinksRepository (val apiClient: DrinksRetrieverApiClient, private val dao: DrinksDao){

    suspend fun fetchAllDrinks():Drinks{
        return apiClient.service.FetchMargaritaCocktails()

    }

    suspend fun insertDrinksIntoRoomDb(drinks: Drinks){
        dao.insert(drinks)
    }

    val drinksFromLocalDatabase = dao.getDrinks()




}