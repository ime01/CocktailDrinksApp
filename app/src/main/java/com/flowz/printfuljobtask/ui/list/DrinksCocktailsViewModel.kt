package com.flowz.printfuljobtask.ui.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowz.printfuljobtask.drinksrepository.DrinksCocktailsRepository
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrinksCocktailsViewModel (private var drinksRepository: DrinksCocktailsRepository): ViewModel() {

    val drinksFromNetwork = MutableLiveData<Drinks>()
    val drinksFromLocalDb = drinksRepository.drinksFromDb


    fun setUp(){

       EspressoIdlingResource.increment()

        viewModelScope.launch(Dispatchers.IO){
            drinksFromNetwork.postValue(drinksRepository.fetchAllDrinks())

//            Sending Drinks from Network into Room Database
            val alldrinks = drinksRepository.fetchAllDrinks()

            for (i in 0 until alldrinks.drinks.size) {

                val dateModified: String? = alldrinks.drinks.get(i).dateModified
                val idDrink: String? = alldrinks.drinks.get(i).idDrink
                val strAlcoholic: String? = alldrinks.drinks.get(i).strAlcoholic
                val strCategory: String? = alldrinks.drinks.get(i).strCategory
                val strCreativeCommonsConfirmed: String? = alldrinks.drinks.get(i).strCreativeCommonsConfirmed
                val strDrink: String? = alldrinks.drinks.get(i).strDrink
                val strDrinkThumb: String? = alldrinks.drinks.get(i).strDrinkThumb
                val strGlass: String? = alldrinks.drinks.get(i).strGlass
                val strIBA: String? = alldrinks.drinks.get(i).strIBA
                val strImageAttribution: String? = alldrinks.drinks.get(i).strImageAttribution
                val strImageSource: String? = alldrinks.drinks.get(i).strImageSource
                val strIngredient1: String? = alldrinks.drinks.get(i).strIngredient1
                val strInstructions: String? = alldrinks.drinks.get(i).strInstructions
                val strInstructionsDE: String? = alldrinks.drinks.get(i).strInstructionsDE
                val strMeasure1: String? = alldrinks.drinks.get(i).strMeasure1

//                       Assign the networkResults to RoomDataBase Object
                val drink = Drink(
                         dateModified,
                 idDrink,
                strAlcoholic,
                strCategory,
                 strCreativeCommonsConfirmed,
                strDrink,
                        strDrinkThumb,
               strGlass,
                 strIBA,
                strImageAttribution,
                strImageSource,
                strIngredient1,
                strInstructions,
                 strInstructionsDE,
                strMeasure1
                )

//                Insert Object Containing all Drinks into Room Database
                    drinksRepository.insertDrinksIntoDb(drink)
//
                }

            }

        }

}