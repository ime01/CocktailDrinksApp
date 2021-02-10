package com.flowz.printfuljobtask.ui.list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flowz.introtooralanguage.adapters.DrinksAdapter
import com.flowz.printfuljobtask.MainActivity
import com.flowz.printfuljobtask.R
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListFragmentTest{

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {

//        launchFragmentInContainer<ListFragment>(fragmentArgs = null, factory = null)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    val LISTITEM = 5

    @Test
    fun testViews() {
        onView(withId(R.id.welcome_text_marquee)).check(matches(withText(R.string.welcome_text)))

        onView(withId(R.id.rv_drinks)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        onView(withId(R.id.rv_drinks))
            .perform(actionOnItemAtPosition<DrinksAdapter.DrinksViewHolder>(LISTITEM, click()))

        onView(withId(R.id.cocktail_instructions)).check(matches(isDisplayed()))
    }

    @Test
    fun test_backNavigation_toDrinkListFragment() {
        onView(withId(R.id.rv_drinks))
            .perform(actionOnItemAtPosition<DrinksAdapter.DrinksViewHolder>(LISTITEM, click()))

        onView(withId(R.id.cocktail_instructions)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.rv_drinks)).check(matches(isDisplayed()))
    }
}