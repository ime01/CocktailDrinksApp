package com.flowz.drinkcocktails.drinksrepository

import androidx.lifecycle.LiveData
import com.flowz.drinkcocktails.drinkroomdb.DrinkDao
import com.flowz.drinkcocktails.models.Drink
import com.flowz.drinkcocktails.models.Drinks
import com.flowz.drinkcocktails.network.ApiServiceCalls
import com.flowz.drinkcocktails.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinksCocktailsRepositoryForTesting @Inject constructor (private val apiClient: ApiServiceCalls, private val dbReference : DrinkDao): TdrinkRepository{
    override suspend fun insertDrink(drink: Drink) {
        dbReference.insert(drink)
    }

    override suspend fun deleteDrink(drink: Drink) {
        dbReference.delete(drink)

    }

    override fun observeAllDrinks(): LiveData<List<Drink>> {
        return dbReference.getDrinks()

    }

    override fun deleteAllDrinks() {
        dbReference.clearAll()

    }

    override suspend fun fetchDrinks(): Resource<Drinks> {
        return try {
            val response = apiClient.FetchMargaritaCocktails("Old Fashioned")
            if(response != null) {
                response?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }

    }

//    val drinksFromDb = dbReference.getDrinks()
//
//
//    suspend fun fetchAllDrinks():Drinks{
//        return apiClient.FetchMargaritaCocktails()
//
//    }
//
//    suspend fun insertDrinksIntoDb(drink: Drink){
//        dbReference.insert(drink)
//    }
//
//    suspend fun clearDrinksInDb(){
//        dbReference.clearAll()
//    }



}