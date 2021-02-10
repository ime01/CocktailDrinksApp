package com.flowz.printfuljobtask.drinkroomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flowz.printfuljobtask.models.Drink

@Database(entities = [Drink::class], version = 1)
abstract class DrinkDatabase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao

    companion object{

        @Volatile private var instance: DrinkDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){

            instance ?: buildDataBase(context).also {
                instance = it
            }

        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(context.applicationContext, DrinkDatabase::class.java, "cocktail_drink.db").fallbackToDestructiveMigration().build()
    }

}

