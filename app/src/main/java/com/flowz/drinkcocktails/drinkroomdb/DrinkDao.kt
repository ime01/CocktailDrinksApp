package com.flowz.drinkcocktails.drinkroomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.drinkcocktails.models.Drink
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (drink: Drink)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinks (drinks: List<Drink>)

    @Query("SELECT * FROM drinks_cocktails_table  WHERE strDrink LIKE  :searchQuery")
    fun searchDrinks(searchQuery: String): Flow<List<Drink>>

    @Delete
    suspend fun delete (drink: Drink)

    @Query("SELECT * FROM drinks_cocktails_table where id = id")
    fun getDrinks() : LiveData<List<Drink>>

    @Query("DELETE FROM drinks_cocktails_table")
    fun clearAll()

}