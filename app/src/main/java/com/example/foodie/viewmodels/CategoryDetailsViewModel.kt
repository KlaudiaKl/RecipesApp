package com.example.foodie.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.models.Category
import com.example.foodie.models.MealX
import com.example.foodie.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsViewModel() :ViewModel() {

    var mealsLiveData =  MutableLiveData<List<MealX>>()

    fun getCategoryDetails(category: String){
        RetrofitInstance.api.getMealsOfCategory(category).enqueue(object : Callback<Category>{
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                mealsLiveData.value = response.body()!!.meals
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                Log.e("CategoryDetails", t.message.toString())
            }

        }
        )
    }

    fun observeCategoryLiveData(): MutableLiveData<List<MealX>> {
        return mealsLiveData
    }
}