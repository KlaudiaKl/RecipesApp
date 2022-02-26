package com.example.foodie.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodie.DB.dataclasses.OwnRecipe
import com.example.foodie.DB.OwnRecipesDatabase
import com.example.foodie.DB.repositories.OwnRecipesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnRecipeViewModel(application: Application) : AndroidViewModel(application) {

    val allRecipes : LiveData<List<OwnRecipe>>
    private val repository : OwnRecipesRepo

    init {
        val ownRecipesDao = OwnRecipesDatabase.getDatabase(application).ownRecipesDao()
        repository = OwnRecipesRepo(ownRecipesDao)
        allRecipes = repository.allRecipes
    }

    fun addRecipe(ownRecipe: OwnRecipe){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecipe(ownRecipe)
        }
    }

    fun updateRecipe(ownRecipe: OwnRecipe){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecipe(ownRecipe)
        }
    }

    fun deleteRecipe(ownRecipe: OwnRecipe){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRecipe(ownRecipe)
        }
    }
}