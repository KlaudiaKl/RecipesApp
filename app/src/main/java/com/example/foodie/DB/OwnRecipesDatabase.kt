package com.example.foodie.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodie.DB.DAOs.OwnRecipesDao
import com.example.foodie.DB.dataclasses.OwnRecipe

@Database(entities = [OwnRecipe::class], version = 1, exportSchema = false)
abstract class OwnRecipesDatabase : RoomDatabase() {

    abstract fun ownRecipesDao() : OwnRecipesDao

    companion object{
        @Volatile //visible to other threads
        private var INSTANCE: OwnRecipesDatabase? = null

        fun getDatabase(context: Context): OwnRecipesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OwnRecipesDatabase::class.java,
                    "own_recipes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}