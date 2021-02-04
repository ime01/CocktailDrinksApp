package com.flowz.printfuljobtask.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowz.printfuljobtask.drinksrepository.DrinksRepository
import com.flowz.printfuljobtask.models.Drinks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrinksListViewModel (private var drinksRepository: DrinksRepository): ViewModel() {

    val drinksFromNetwork = MutableLiveData<Drinks>()
    val drinksFromLocalDb = MutableLiveData<Drinks>()

    fun setUp(){
        viewModelScope.launch(Dispatchers.Default){
//            drinksFromNetwork.postValue(DrinksRepository(DrinksRetrieverApiClient(),).fetchAllDrinks())

            drinksFromNetwork.postValue(drinksRepository.fetchAllDrinks())
//
//            Sending Drinks from Network into Room Database
//            val drinks = drinksRepository.fetchAllDrinks()
//            drinksRepository.insertDrinksIntoRoomDb(drinks)
////
////          Fetching drnks from RoomdB
//            drinksFromLocalDb.postValue(drinksRepository.drinksFromLocalDatabase.value)

        }



    }
}