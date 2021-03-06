package com.flowz.printfuljobtask.drinksrepository

import com.flowz.printfuljobtask.drinkroomdb.DrinkDao
import com.flowz.printfuljobtask.drinkroomdb.DrinkDatabase
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.network.ApiServiceCalls
import com.flowz.printfuljobtask.network.DrinksRetrieverApiClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinksCocktailsRepository @Inject constructor (private val apiClient: ApiServiceCalls, private val dbReference : DrinkDao){

    val drinksFromDb = dbReference.getDrinks()


    suspend fun fetchAllDrinks(drinkType: String):Drinks{
        return apiClient.FetchMargaritaCocktails(drinkType)
    }

    fun searchDrinks(searchQuery: String): Flow<List<Drink>> {
        return dbReference.searchDrinks(searchQuery)
    }

    suspend fun insertDrinksIntoDb(drink: Drink){
        dbReference.insert(drink)
    }

    suspend fun insertListOfDrinksIntoDb(drinks: List<Drink>){
        dbReference.insertDrinks(drinks)
    }

    suspend fun clearDrinksInDb(){
        dbReference.clearAll()
    }



}