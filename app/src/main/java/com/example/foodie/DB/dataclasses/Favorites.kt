package com.example.foodie.DB.dataclasses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_recipes")
@Parcelize
data class Favorites (
    val mealName: String,
    @PrimaryKey
    val mealId: Int,
    val mealThumb: String
    //val mealDescription: String,

) : Parcelable {

}