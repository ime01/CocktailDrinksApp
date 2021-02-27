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


//    init {
//        setUp()
//    }

//    fun setUp(){
//
//       EspressoIdlingResource.increment()
//
//        viewModelScope.launch(Dispatchers.IO){
//            try {
////                drinksFromNetwork.postValue(drinksRepository.fetchAllDrinks())
//                drinksFromNetwork.postValue(drinksRepository.fetchDrinks())
//
////            Sending Drinks from Network into Room Database
////                val alldrinks = drinksRepository.fetchAllDrinks()
//                val alldrinks = drinksRepository.fetchDrinks()
//
//                for (i in 0 until alldrinks.drinks.size) {
//
//                    val dateModified: String? = alldrinks.drinks.get(i).dateModified
//                    val idDrink: String? = alldrinks.drinks.get(i).idDrink
//                    val strAlcoholic: String? = alldrinks.drinks.get(i).strAlcoholic
//                    val strCategory: String? = alldrinks.drinks.get(i).strCategory
//                    val strCreativeCommonsConfirmed: String? = alldrinks.drinks.get(i).strCreativeCommonsConfirmed
//                    val strDrink: String? = alldrinks.drinks.get(i).strDrink
//                    val strDrinkThumb: String? = alldrinks.drinks.get(i).strDrinkThumb
//                    val strGlass: String? = alldrinks.drinks.get(i).strGlass
//                    val strIBA: String? = alldrinks.drinks.get(i).strIBA
//                    val strImageAttribution: String? = alldrinks.drinks.get(i).strImageAttribution
//                    val strImageSource: String? = alldrinks.drinks.get(i).strImageSource
//                    val strIngredient1: String? = alldrinks.drinks.get(i).strIngredient1
//                    val strInstructions: String? = alldrinks.drinks.get(i).strInstructions
//                    val strInstructionsDE: String? = alldrinks.drinks.get(i).strInstructionsDE
//                    val strMeasure1: String? = alldrinks.drinks.get(i).strMeasure1
//
////                       Assign the networkResults to RoomDataBase Object
//                    val drink = Drink(
//                        dateModified,
//                        idDrink,
//                        strAlcoholic,
//                        strCategory,
//                        strCreativeCommonsConfirmed,
//                        strDrink,
//                        strDrinkThumb,
//                        strGlass,
//                        strIBA,
//                        strImageAttribution,
//                        strImageSource,
//                        strIngredient1,
//                        strInstructions,
//                        strInstructionsDE,
//                        strMeasure1
//                    )
//
////                Insert Object Containing all Drinks into Room Database
////                    drinksRepository.clearDrinksInDb()
////                    drinksRepository.insertDrinksIntoDb(drink)
//                    drinksRepository.insertDrink(drink)
////
//                }
//
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//
//
//
//            }
//
//        }

}