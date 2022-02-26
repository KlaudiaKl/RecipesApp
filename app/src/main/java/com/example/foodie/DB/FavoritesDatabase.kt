package com.example.foodie.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodie.DB.DAOs.FavoritesDao
import com.example.foodie.DB.dataclasses.Favorites


@Database(entities = [Favorites::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase: RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object{
        @Volatile //visible to other threads
        private var INSTANCE: FavoritesDatabase? = null

        fun getDatabase(context: Context): FavoritesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java,
                    "favorite_recipes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}