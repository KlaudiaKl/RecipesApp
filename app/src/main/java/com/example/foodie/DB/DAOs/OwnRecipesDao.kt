package com.example.foodie.DB.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodie.DB.dataclasses.OwnRecipe

@Dao
interface OwnRecipesDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipe(ownRecipe: OwnRecipe)

    @Delete
    fun deleteRecipe(ownRecipe: OwnRecipe)

    @Update
    fun updateRecipe(ownRecipe: OwnRecipe)

    @Query("SELECT * FROM own_recipes")
    fun getAllOwnRecipes(): LiveData<List<OwnRecipe>>

    @Query("SELECT * FROM own_recipes where id = :id ")
    fun getRecipeById(id: Int) : LiveData<OwnRecipe>
}