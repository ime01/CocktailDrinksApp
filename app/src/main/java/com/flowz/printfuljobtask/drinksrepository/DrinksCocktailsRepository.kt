package com.flowz.printfuljobtask.drinksrepository

import com.flowz.printfuljobtask.drinkroomdb.DrinkDao
import com.flowz.printfuljobtask.drinkroomdb.DrinkDatabase
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.network.DrinksRetrieverApiClient


class DrinksCocktailsRepository (private val apiClient: DrinksRetrieverApiClient, private val dbReference : DrinkDao){

//    val drinkDao = dbReference.drinkDao()

    val drinksFromDb = dbReference.getDrinks()


    suspend fun fetchAllDrinks():Drinks{
        return apiClient.service.FetchMargaritaCocktails()

    }

    suspend fun insertDrinksIntoDb(drink: Drink){
        dbReference.insert(drink)
    }



}