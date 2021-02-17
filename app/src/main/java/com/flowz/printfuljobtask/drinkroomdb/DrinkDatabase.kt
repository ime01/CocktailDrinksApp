package com.flowz.printfuljobtask.drinkroomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flowz.printfuljobtask.models.Drink

@Database(entities = [Drink::class], version = 1)
abstract class DrinkDatabase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao
}

