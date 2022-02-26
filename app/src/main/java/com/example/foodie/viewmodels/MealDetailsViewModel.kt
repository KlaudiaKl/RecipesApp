package com.example.foodie.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.models.Meal
import com.example.foodie.models.RandomMeal
import com.example.foodie.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsViewModel():ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()

    init {

    }

    fun getMealDetails(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<RandomMeal>{
            override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
                if(response.body()!=null){
                    mealDetailLiveData.value = response.body()!!.meals[0]
                }
                else return
            }

            override fun onFailure(call: Call<RandomMeal>, t: Throwable) {
                Log.e("MealActivity", t.message.toString())
            }

        })
    }

    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailLiveData
    }


}