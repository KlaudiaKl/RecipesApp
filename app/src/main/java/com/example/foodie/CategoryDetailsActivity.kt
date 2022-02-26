package com.example.foodie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodie.adapters.CategoryMealsAdapter
import com.example.foodie.databinding.ActivityCategoryDetailsBinding
import com.example.foodie.viewmodels.CategoryDetailsViewModel

class CategoryDetailsActivity : AppCompatActivity() {

    lateinit var binding : ActivityCategoryDetailsBinding
    lateinit var viewModel: CategoryDetailsViewModel
    lateinit var categoryMealsadapter : CategoryMealsAdapter

    companion object{

        const val CATEGORY_NAME = "com.example.foodie.nameCategory"
        const val CATEGORY_DESCRIPTION = "com.example.descriptionCategory"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryMealsadapter = CategoryMealsAdapter()


        setRecyclerView()
        binding.tvCategoryHeader.text = intent.getStringExtra(CATEGORY_NAME)
        binding.tvCategoryDescription.text = intent.getStringExtra(CATEGORY_DESCRIPTION)

        viewModel = ViewModelProviders.of(this)[CategoryDetailsViewModel::class.java]
        viewModel.getCategoryDetails(intent.getStringExtra(CATEGORY_NAME)!!)

        viewModel.observeCategoryLiveData().observe(this, Observer {
            mealList -> categoryMealsadapter.setMealsList(mealList)

        })

        onItemClick()
    }

    private fun onItemClick() {
        categoryMealsadapter.onItemClick = {
            meal ->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(MealActivity.MEAL_ID, meal.idMeal)
            intent.putExtra(MealActivity.MEAL_NAME, meal.strMeal)
            intent.putExtra(MealActivity.MEAL_THUMB, meal.strMealThumb)
            //intent.putExtra(SOURCE, "CategoryDetailsActivity")
            startActivity(intent)
        }
    }

    private fun setRecyclerView() {
        binding.rvCategoryMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsadapter
        }
    }

}