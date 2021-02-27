package com.flowz.printfuljobtask.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flowz.printfuljobtask.drinksrepository.FakeDrinkRepository
import com.flowz.printfuljobtask.getOrAwaitValueTest
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
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