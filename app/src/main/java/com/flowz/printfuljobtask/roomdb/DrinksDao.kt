package com.flowz.printfuljobtask.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.printfuljobtask.models.Converters
import com.flowz.printfuljobtask.models.Drinks

@Dao
interface DrinksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drinks: Drinks)

//    @TypeConverters(Converters::class)
    @Query("SELECT * FROM cocktails_table")
    fun getDrinks():LiveData<Drinks>
}