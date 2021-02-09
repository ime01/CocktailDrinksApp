package com.flowz.printfuljobtask.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flowz.printfuljobtask.drinksrepository.DrinksCocktailsRepository

class DrinksCocktailViewModelFactory (private  val repository: DrinksCocktailsRepository):ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinksCocktailsViewModel(repository) as T
    }
}