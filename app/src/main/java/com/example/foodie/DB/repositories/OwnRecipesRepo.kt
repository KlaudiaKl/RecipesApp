package com.example.foodie.DB.repositories

import androidx.lifecycle.LiveData
import com.example.foodie.DB.DAOs.OwnRecipesDao
import com.example.foodie.DB.dataclasses.OwnRecipe

class OwnRecipesRepo(private val ownRecipesDao: OwnRecipesDao) {

    val allRecipes: LiveData<List<OwnRecipe>> = ownRecipesDao.getAllOwnRecipes()

    suspend fun addRecipe(ownRecipe: OwnRecipe){
        ownRecipesDao.insertRecipe(ownRecipe)
    }
    suspend fun updateRecipe(ownRecipe: OwnRecipe){
        ownRecipesDao.updateRecipe(ownRecipe)
    }

    suspend fun deleteRecipe(ownRecipe: OwnRecipe){
        ownRecipesDao.deleteRecipe(ownRecipe)
    }
}