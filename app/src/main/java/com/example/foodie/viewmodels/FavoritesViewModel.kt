package com.example.foodie.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import androidx.lifecycle.viewModelScope
import com.example.foodie.DB.dataclasses.Favorites
import com.example.foodie.DB.FavoritesDatabase
import com.example.foodie.DB.repositories.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoritesViewModel(application: Application):AndroidViewModel(application) {

    val readAllData: LiveData<List<Favorites>>
    private val repository: FavoritesRepository


    init {
        val favoritesDao = FavoritesDatabase.getDatabase(application).favoritesDao()
        repository = FavoritesRepository(favoritesDao)
        readAllData = repository.readAllData
    }

    fun addFavorite( favorites: Favorites){ //running in the background
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(favorites)
        }
    }



    fun deleteFavorite( favorites: Favorites){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(favorites)
        }
    }
}