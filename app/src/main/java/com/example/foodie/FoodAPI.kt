package com.example.foodie

import com.example.foodie.models.Category
import com.example.foodie.models.CategoryList
import com.example.foodie.models.MealsSearch
import com.example.foodie.models.RandomMeal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodAPI {
    @GET("random.php")
    fun getRandomMeal(): Call<RandomMeal>

    @GET ("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<RandomMeal>

    @GET("filter.php?")
    fun getMealsOfCategory(@Query("c") category: String): Call<Category>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("search.php")
    fun searchByName(@Query("s")s : String): Call<MealsSearch>

    @GET("lookup.php")
    fun getMealById(@Query("i")i: String): Call<RandomMeal>
}