package com.flowz.drinkcocktails.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOUCRE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOUCRE)

    fun increment(){
        countingIdlingResource.increment()
    }

    fun decrement(){
        if (!countingIdlingResource.isIdleNow){
            countingIdlingResource.decrement()
        }
    }

}