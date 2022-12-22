package com.flowz.drinkcocktails.drinkroomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flowz.drinkcocktails.models.Drink

@Database(entities = [Drink::class], version = 1, exportSchema = false)
abstract class DrinkDatabase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao
}

