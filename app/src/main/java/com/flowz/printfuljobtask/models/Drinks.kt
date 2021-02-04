package com.flowz.printfuljobtask.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "cocktails_table")
data class Drinks(
    val drinks: List<Drink>
){
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

}

//class Converters {
//
//    @TypeConverter
//    fun stringAsStringList(strings: String?): List<String>{
//
//        val list = mutableListOf<String>()
//        strings
//            ?.split(",")
//            ?.forEach{
//                list.add(it)
//            }
//
//        return list
//    }
////    fun listToJson(value: List<CurrentWeatherEntry>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun stringListAsString(strings: List<String>): String{
//
//        var result = ""
//        strings?.forEach{element->
//            result += "$element,"
//
//        }
//        return result.removeSuffix(",")
//    }
//
//}