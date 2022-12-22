package com.flowz.drinkcocktails.drinksrepository

import com.flowz.drinkcocktails.drinkroomdb.DrinkDao
import com.flowz.drinkcocktails.models.Drink
import com.flowz.drinkcocktails.models.Drinks
import com.flowz.drinkcocktails.network.ApiServiceCalls
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinksCocktailsRepository @Inject constructor (private val apiClient: ApiServiceCalls, private val dbReference : DrinkDao) {

    val drinksFromDb = dbReference.getDrinks()


    suspend fun fetchAllDrinks(drinkType: String):Drinks{
        return apiClient.FetchMargaritaCocktails(drinkType)
    }

    fun searchDrinks(searchQuery: String): Flow<List<Drink>> {
        return dbReference.searchDrinks(searchQuery)
    }

    suspend fun insertDrinkIntoDb(drink: Drink){
        dbReference.insert(drink)
    }

    suspend fun insertListOfDrinksIntoDb(drinks: List<Drink>){
        dbReference.insertDrinks(drinks)
    }

    suspend fun deleteDrinkInDb(drink: Drink){
        dbReference.delete(drink)
    }

    suspend fun clearAllDrinksInDb(){
        dbReference.clearAll()
    }



}