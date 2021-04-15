package com.flowz.printfuljobtask.drinksrepository

import androidx.lifecycle.LiveData
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.utils.Resource

interface TdrinkRepository {


    suspend fun insertDrink(drink: Drink)

    suspend fun deleteDrink(drink: Drink)

    fun observeAllDrinks(): LiveData<List<Drink>>

    fun deleteAllDrinks()

   suspend fun fetchDrinks(): Resource<Drinks>




}