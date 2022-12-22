package com.flowz.drinkcocktails.ui.list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.flowz.drinkcocktails.drinksrepository.DrinksCocktailsRepository
import com.flowz.drinkcocktails.models.Drink
import com.flowz.drinkcocktails.models.Drinks
import com.flowz.drinkcocktails.utils.EspressoIdlingResource
import com.flowz.drinkcocktails.utils.Resource2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext




class DrinksCocktailsViewModel @ViewModelInject constructor (private var drinksRepository: DrinksCocktailsRepository): ViewModel() {

    val drinksFromNetwork = MutableLiveData<Resource2<Drinks>>()
    val drinksFromLocalDb = drinksRepository.drinksFromDb


    fun searchDrinkFromDb(searchQuery: String): LiveData<List<Drink>> {
        return drinksRepository.searchDrinks(searchQuery).asLiveData()

    }

    fun searchDrnkTypeFromNetwork(drinkType: String){

       EspressoIdlingResource.increment()

        viewModelScope.launch(Dispatchers.IO){
            try {

                withContext(Dispatchers.Main){
                    drinksFromNetwork.value = Resource2.loading()
                }

                val dataSet = Resource2.success(drinksRepository.fetchAllDrinks(drinkType))

                withContext(Dispatchers.Main){
                    if (dataSet.data?.drinks?.isNotEmpty() == true){
                        drinksFromNetwork.value = dataSet
                    }
                }

//              Sending Drinks from Network into Room Database
                if (dataSet.data?.drinks?.isNotEmpty() == true){
                    drinksRepository.insertListOfDrinksIntoDb(dataSet.data.drinks)
                }



            }catch (e:Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    drinksFromNetwork.value = Resource2.error( e.toString())
                }
            }

            }
        }


     fun deleteDrink(drink: Drink){
         viewModelScope.launch (Dispatchers.IO){
             drinksRepository.deleteDrinkInDb(drink)
         }
    }

    fun clearAllDrinks(){
        viewModelScope.launch (Dispatchers.IO){
            drinksRepository.clearAllDrinksInDb()
        }
    }

    fun insertDrink(drink: Drink){
        viewModelScope.launch (Dispatchers.IO){
            drinksRepository.insertDrinkIntoDb(drink)
        }

    }

    companion object{
        const val TAG = "DrinksVM"
    }

}