package com.example.foodie.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.*
import com.example.foodie.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var mealsOfCategoryLiveData = MutableLiveData<List<MealX>>()
    private var categoriesLiveData = MutableLiveData<List<CategoryX>>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<RandomMeal> {
            override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
                if(response.body()!=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    Log.d("TEST", "${randomMeal.idMeal}, ${randomMeal.strMeal}")
                    randomMealLiveData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<RandomMeal>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getMealsOfCategory(category: String){
        RetrofitInstance.api.getMealsOfCategory(category).enqueue(object: Callback<Category>{
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                if(response.body() != null){

                    mealsOfCategoryLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                Log.e("HomeFragment", "meals of category")
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                categoriesLiveData.value = response.body()!!.categories
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }

        })
    }

    fun observeRandomMealLivedata(): LiveData<Meal>{
        return randomMealLiveData
    }

    fun observeMealsOfCategoryLiveData(): LiveData<List<MealX>>{
        return mealsOfCategoryLiveData
    }

    fun observeCategoryLiveData(): LiveData<List<CategoryX>>{
        return categoriesLiveData
    }
}