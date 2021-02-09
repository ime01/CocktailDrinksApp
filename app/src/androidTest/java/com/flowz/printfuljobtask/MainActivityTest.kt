package com.flowz.printfuljobtask

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @get:Rule
//    @Rule @JvmField
//    var activityRule = ActivityTestRule <MainActivity>(MainActivity::class.java)

    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun confirmView() {
        onView(withId(R.id.welcome_text_marquee)).check(matches(isDisplayed()))
    }
}
