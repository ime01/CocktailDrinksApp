package com.flowz.drinkcocktails.models



import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "drinks_cocktails_table")
@Parcelize
data class Drink(
    
    val dateModified: String? = null,
    val idDrink: String? = null,
//    val strAlcoholic: String? = null,
    val strCategory: String? = null,
//    val strCreativeCommonsConfirmed: String? = null,
    val strDrink: String? = null,
    val strDrinkThumb: String? = null,
//    val strGlass: String? = null,
    val strIBA: String? = null,
    val strImageAttribution: String? = null,
    val strImageSource: String? = null,
    val strIngredient1: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strIngredient6: String? = null,
    val strIngredient7: String? = null,
    val strInstructions: String? = null,
//    val strInstructionsDE: String? = null,
    val strMeasure1: String? = null,
    val strMeasure2: String? = null,
    val strMeasure3: String? = null,
    val strMeasure4: String? = null,
    val strMeasure5: String? = null,
    val strMeasure6: String? = null,
    val strMeasure7: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Parcelable
