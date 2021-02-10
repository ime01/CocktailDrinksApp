package com.flowz.printfuljobtask.drinkroomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.printfuljobtask.models.Drink

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (drink: Drink)

    @Query("SELECT * FROM drinks_cocktails_table where id = id")
    fun getDrinks() : LiveData<List<Drink>>

}