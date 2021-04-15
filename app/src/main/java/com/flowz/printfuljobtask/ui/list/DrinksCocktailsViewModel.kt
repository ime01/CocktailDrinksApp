package com.flowz.printfuljobtask.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

}