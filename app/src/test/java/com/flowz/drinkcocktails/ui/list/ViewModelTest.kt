package com.flowz.drinkcocktails.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flowz.drinkcocktails.drinksrepository.FakeDrinkRepository
import com.flowz.drinkcocktails.getOrAwaitValueTest
import com.flowz.drinkcocktails.utils.Status
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DrinksCocktailsViewModelForTesting
//
    @Before
    fun setUp(){
        viewModel = DrinksCocktailsViewModelForTesting(FakeDrinkRepository())
    }

    @Test
    fun `empty fieldreturns error`(){

        viewModel.insertdrink(
            "",
            1,
            "hello",
            "",
            "",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            4
        )

        val value = viewModel.insertDrinkItemStatus.getOrAwaitValueTest ()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)


    }

}