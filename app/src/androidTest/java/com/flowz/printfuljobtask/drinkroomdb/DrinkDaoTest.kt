package com.flowz.printfuljobtask.drinkroomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.flowz.printfuljobtask.getOrAwaitValue
import com.flowz.printfuljobtask.models.Drink
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DrinkDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DrinkDatabase
    private lateinit var dao: DrinkDao

    @Before
    fun Setup(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DrinkDatabase::class.java).allowMainThreadQueries().build()
        dao = database.drinkDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertDrink() = runBlockingTest{
        val drink = Drink(
             "hello",
            "1",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            1
        )

        dao.insert(drink)

        val allDrinks = dao.getDrinks().getOrAwaitValue()

        assertThat(allDrinks).contains(drink)
    }


    @Test
    fun deleteDrink() = runBlockingTest{
        val drink = Drink(
            "hello",
            "2",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            "hello",
            2
        )

        dao.delete(drink)

        val allDrinks = dao.getDrinks().getOrAwaitValue()

        assertThat(allDrinks).doesNotContain(drink)
    }
}