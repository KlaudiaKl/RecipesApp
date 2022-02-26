package com.example.foodie.DB.repositories

import androidx.lifecycle.LiveData
import com.example.foodie.DB.DAOs.FavoritesDao
import com.example.foodie.DB.dataclasses.Favorites

class FavoritesRepository(private val favoritesDao: FavoritesDao) {

    val readAllData: LiveData<List<Favorites>> = favoritesDao.getAllFavorites()

    suspend fun addFavorite(favorites: Favorites){
        favoritesDao.insertFavorite(favorites)
    }

    suspend fun deleteFavorite(favorite: Favorites){
        favoritesDao.deleteFavorite(favorite)
    }
}