package com.flowz.drinkcocktails.drinksrepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flowz.drinkcocktails.models.Drink
import com.flowz.drinkcocktails.models.Drinks
import com.flowz.drinkcocktails.utils.Resource

class FakeDrinkRepository: TdrinkRepository {

    private val drinksItems = mutableListOf<Drink>()

    private val observableDrinkItems = MutableLiveData<List<Drink>>(drinksItems)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observableDrinkItems.postValue(drinksItems)
    }

    override suspend fun insertDrink(drink: Drink) {
        drinksItems.add(drink)
        refreshLiveData()

    }

    override suspend fun deleteDrink(drink: Drink) {
        drinksItems.remove(drink)
        refreshLiveData()

    }

    override fun observeAllDrinks(): LiveData<List<Drink>> {
        return observableDrinkItems
    }

    override fun deleteAllDrinks() {
        drinksItems.clear()
        refreshLiveData()
    }

    override suspend fun fetchDrinks(): Resource<Drinks> {
        return if (shouldReturnNetworkError){
           Resource.error("Network Error", null)
        }else{
            Resource.success(Drinks(listOf()))
        }

    }

}