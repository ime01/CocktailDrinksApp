package com.flowz.printfuljobtask.models



import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "drinks_cocktails_table")
@Parcelize
data class Drink(
    
    val dateModified: String? = null,
    val idDrink: String? = null,
    val strAlcoholic: String? = null,
    val strCategory: String? = null,
    val strCreativeCommonsConfirmed: String? = null,
    val strDrink: String? = null,
    val strDrinkThumb: String? = null,
    val strGlass: String? = null,
    val strIBA: String? = null,
    val strImageAttribution: String? = null,
    val strImageSource: String? = null,
    val strIngredient1: String? = null,
    val strInstructions: String? = null,
    val strInstructionsDE: String? = null,
    val strMeasure1: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Parcelable
