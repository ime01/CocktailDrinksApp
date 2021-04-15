package com.flowz.printfuljobtask.ui.list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.flowz.printfuljobtask.drinksrepository.DrinksCocktailsRepository
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrinksCocktailsViewModel @ViewModelInject constructor (private var drinksRepository: DrinksCocktailsRepository): ViewModel() {

    val drinksFromNetwork = MutableLiveData<Drinks>()
    val drinksFromLocalDb = drinksRepository.drinksFromDb


    init {
        setUp()
    }

    fun searchDrink(searchQuery: String): LiveData<List<Drink>> {
        return drinksRepository.searchDrinks(searchQuery).asLiveData()
        Log.d(TAG, "Searched Successfull")
    }

    fun setUp(){

       EspressoIdlingResource.increment()

        viewModelScope.launch(Dispatchers.IO){
            try {
                drinksFromNetwork.postValue(drinksRepository.fetchAllDrinks())

//            Sending Drinks from Network into Room Database
                val alldrinks = drinksRepository.fetchAllDrinks()

                drinksRepository.insertListOfDrinksIntoDb(alldrinks.drinks)

            }catch (e:Exception){
                e.printStackTrace()
            }

            }
        }

    companion object{
        const val TAG = "DrinksVM"
    }

}