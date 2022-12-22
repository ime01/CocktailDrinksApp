package com.flowz.drinkcocktails.drinksrepository

import androidx.lifecycle.LiveData
import com.flowz.drinkcocktails.models.Drink
import com.flowz.drinkcocktails.models.Drinks
import com.flowz.drinkcocktails.utils.Resource

interface TdrinkRepository {


    suspend fun insertDrink(drink: Drink)

    suspend fun deleteDrink(drink: Drink)

    fun observeAllDrinks(): LiveData<List<Drink>>

    fun deleteAllDrinks()

   suspend fun fetchDrinks(): Resource<Drinks>




}