package com.example.foodie.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.RetrofitInstance
import com.example.foodie.models.Meal
import com.example.foodie.models.MealsSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(): ViewModel() {

    private var searchLiveData = MutableLiveData<List<Meal>>()

    fun searchMeals(name: String, context: Context?){
        RetrofitInstance.api.searchByName(name).enqueue(object  : Callback<MealsSearch>{

            override fun onResponse(call: Call<MealsSearch>, response: Response<MealsSearch>) {
                Log.i("Retrofit", call.request().url().toString())
                Log.i("Retrofit", response.body().toString())
                if(response.body()?.meals==null){
                    Toast.makeText(context?.applicationContext,"No meals found", Toast.LENGTH_LONG).show()
                }else {
                    searchLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsSearch>, t: Throwable) {
                Log.e("SearchFragment", t.message.toString())
            }

        })
    }

    fun observerSearchLiveData(): MutableLiveData<List<Meal>>{
        return searchLiveData
    }
}