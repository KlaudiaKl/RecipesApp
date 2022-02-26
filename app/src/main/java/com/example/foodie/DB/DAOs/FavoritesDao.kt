package com.example.foodie.DB.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodie.DB.dataclasses.Favorites

@Dao
interface FavoritesDao {

    @Insert( onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(meal: Favorites)

    @Delete
    fun deleteFavorite(favorite: Favorites)

    @Update
    fun updateFavorite(meal: Favorites)

    @Query("SELECT * FROM favorite_recipes")
    fun getAllFavorites(): LiveData<List<Favorites>>
}