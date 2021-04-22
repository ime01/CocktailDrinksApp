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
import kotlinx.coroutines.withContext

enum class  DrinkApiStatus {LOADING, ERROR, DONE}


class DrinksCocktailsViewModel @ViewModelInject constructor (private var drinksRepository: DrinksCocktailsRepository): ViewModel() {

    val drinksFromNetwork = MutableLiveData<Drinks>()
    val drinksFromLocalDb = drinksRepository.drinksFromDb
    val drinkNetworkstatus = MutableLiveData<DrinkApiStatus>()


    fun searchDrinkFromDb(searchQuery: String): LiveData<List<Drink>> {
        return drinksRepository.searchDrinks(searchQuery).asLiveData()
        Log.d(TAG, "Searched Successfull")
    }

    fun searchDrnkTypeFromNetwork(drinkType: String){

       EspressoIdlingResource.increment()

        viewModelScope.launch(Dispatchers.IO){
            try {
                withContext(Dispatchers.Main){
                    drinkNetworkstatus.value = DrinkApiStatus.LOADING
                }

                drinksFromNetwork.postValue(drinksRepository.fetchAllDrinks(drinkType))

                withContext(Dispatchers.Main){
                    drinkNetworkstatus.value = DrinkApiStatus.DONE
                }
//            Sending Drinks from Network into Room Database
                val alldrinks = drinksRepository.fetchAllDrinks(drinkType)

                drinksRepository.insertListOfDrinksIntoDb(alldrinks.drinks)

            }catch (e:Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    drinkNetworkstatus.value = DrinkApiStatus.ERROR
                }
            }

            }
        }

    companion object{
        const val TAG = "DrinksVM"
    }

}