package com.example.foodie.DB.dataclasses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "own_recipes")
data class OwnRecipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val mealName: String,
    val mealCategory: String,
    val mealIngredients: String,
    val mealInstructions: String
    ): Parcelable {
}