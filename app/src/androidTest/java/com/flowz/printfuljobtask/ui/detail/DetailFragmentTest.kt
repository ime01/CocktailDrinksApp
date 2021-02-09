package com.flowz.printfuljobtask.ui.detail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flowz.printfuljobtask.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailFragmentTest{

    //    @Rule
//    @JvmField
//    var mActivityTestRule = ActivityScenarioRule(DetailFragmentTest::class.java)
    @get:Rule
    val scenario = launchFragmentInContainer<DetailFragment>(fragmentArgs = null, factory = null)
    @Test
    fun confirmViews() {



        onView(withId(R.id.cocktail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.cocktail_id)).check(matches(isDisplayed()))
        onView(withId(R.id.cocktail_instructions)).check(matches(isDisplayed()))


    }
}