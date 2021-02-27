package com.flowz.printfuljobtask.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flowz.printfuljobtask.drinksrepository.TdrinkRepository
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.utils.Event
import com.flowz.printfuljobtask.utils.Resource

class DrinksCocktailsViewModelForTesting @ViewModelInject constructor (private var drinksRepository: TdrinkRepository): ViewModel() {

    private val _insertDrinkItemStatus = MutableLiveData<Event<Resource<Drink>>>()
    val insertDrinkItemStatus: LiveData<Event<Resource<Drink>>> = _insertDrinkItemStatus

    val drinksFromNetwork = MutableLiveData<Resource<Drinks>>()
//    val drinksFromLocalDb = drinksRepository.drinksFromDb
    val odrinksFromLocalDb = drinksRepository.observeAllDrinks()

   suspend fun insert (drink: Drink){
        drinksRepository.insertDrink(drink)
    }
    suspend fun delete (drink: Drink){
        drinksRepository.deleteDrink(drink)
    }
    suspend fun clearAll (){
        drinksRepository.deleteAllDrinks()
    }

 fun insertdrink(

         hello:String,
         num: Int,
         hello1:String,
         hello2:String,
         hello3:String,
         hello4:String,
         hello5:String,
         hello6:String,
         hello7:String,
         hello8:String,
         hello9:String,
         hello10:String,
         hello11:String,
         hello13:String,
         numb1: Int){

     if(hello.isEmpty() || hello2.isEmpty() || hello3.isEmpty()) {
         _insertDrinkItemStatus.postValue(Event(Resource.error("The fields must not be empty", null)))
         return
     }


 }

}
