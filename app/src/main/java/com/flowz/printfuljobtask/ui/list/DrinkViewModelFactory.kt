package com.flowz.printfuljobtask.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flowz.printfuljobtask.drinksrepository.DrinksRepository

class DrinkViewModelFactory (private  val repository: DrinksRepository):ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinksListViewModel(repository) as T
    }
}