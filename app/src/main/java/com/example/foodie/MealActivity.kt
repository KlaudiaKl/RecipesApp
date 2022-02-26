package com.example.foodie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodie.DB.dataclasses.Favorites
import com.example.foodie.adapters.PopularMealsAdapter
import com.example.foodie.databinding.ActivityMealBinding
import com.example.foodie.models.Meal
import com.example.foodie.models.MealX
import com.example.foodie.viewmodels.FavoritesViewModel
import com.example.foodie.viewmodels.HomeViewModel
import com.example.foodie.viewmodels.MealDetailsViewModel
import com.google.android.material.snackbar.Snackbar

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var intentSource: String
    private lateinit var mealsOfCategoryAdapter: PopularMealsAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mealCategory: String
    private lateinit var favoritesViewModel: FavoritesViewModel



    companion object{
       const val MEAL_ID = "com.example.foodie.idMeal"
       const val MEAL_NAME = "com.example.foodie.nameMeal"
       const val MEAL_THUMB = "com.example.foodie.thumbMeal"
       const val CATEGORY_NAME = "com.example.foodie.nameCategory"
       const val CATEGORY_DESCRIPTION = "com.example.descriptionCategory"

   }


    private lateinit var mealDetailsViewModel: MealDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealsOfCategoryAdapter = PopularMealsAdapter()
        homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        setRecyclerView()

        mealDetailsViewModel = ViewModelProviders.of(this)[MealDetailsViewModel::class.java]

        getMealExtras()
        setDataInViews()
        mealDetailsViewModel.getMealDetails(mealId)
        observerMealDetailsLiveData()

        observerMealsOfCategoryLiveData()
        onItemClick()

        binding.floatingAddToFav.setOnClickListener {
            view ->
            insertDataToDatabase()
            Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_SHORT).setAction("UNDO", null).show()
        }


    }

    private fun insertDataToDatabase() {
        val favorite = Favorites(mealName, mealId = mealId.toInt(), mealThumb)
        favoritesViewModel.addFavorite(favorite)

    }

    private fun observerMealDetailsLiveData() {
        mealDetailsViewModel.observeMealDetailsLiveData().observe(this, object: Observer<Meal>{
            override fun onChanged(t: Meal?) {
                val meal = t

                val ingredientsList = listOf<String>(meal!!.strIngredient1,
                    meal!!.strIngredient2,
                    meal!!.strIngredient3,
                    meal!!.strIngredient4,
                    meal!!.strIngredient5,
                    meal!!.strIngredient6,
                    meal!!.strIngredient7,
                    meal!!.strIngredient8,
                    meal!!.strIngredient9,
                    meal!!.strIngredient10,
                    meal!!.strIngredient11,
                    meal!!.strIngredient12,
                    meal!!.strIngredient13,
                    meal!!.strIngredient14,
                    meal!!.strIngredient15)
                val filteredList =ingredientsList.filter { !it.isNullOrEmpty() }.toList()

                val ingredientsString = filteredList.joinToString {
                    it-> "${it}\n"
                }

                val measuresList = listOf<String>(meal!!.strMeasure1,
                    meal!!.strMeasure2,
                    meal!!.strMeasure3,
                    meal!!.strMeasure4,
                    meal!!.strMeasure5,
                    meal!!.strMeasure6,
                    meal!!.strMeasure7,
                    meal!!.strMeasure8,
                    meal!!.strMeasure9,
                    meal!!.strMeasure10,
                    meal!!.strMeasure11,
                    meal!!.strMeasure12,
                    meal!!.strMeasure13,
                    meal!!.strMeasure14,
                    meal!!.strMeasure15)
                val filteredMeasuresList =measuresList.filter { !it.isNullOrEmpty() }.toList()

                val measuresString = filteredMeasuresList.joinToString {
                        it-> "${it}\n"
                }
                binding.tvDetailsCat.text = "Category: ${meal!!.strCategory}"
                binding.tvDetailsArea.text = "Area: ${meal!!.strArea}"
                binding.instructions.text = meal.strInstructions
                binding.ingredients.text = ingredientsString.replace(",","").dropLast(1)
                binding.measures.text = measuresString.replace(",","").dropLast(1)
                binding.moreHeader.text = "More from category ${meal.strCategory}"
                homeViewModel.getMealsOfCategory(meal.strCategory)


            }

        })
    }


    private fun setDataInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.mealImage)
        binding.collapsingToolBar.title = mealName
        binding.instructionsHeader.text = "How to make ${mealName}:"
    }

    private fun getMealExtras() {
        val intent = intent

        //Log.d("type", intentSource)
        mealId = intent.getStringExtra(MEAL_ID)!!
        mealName = intent.getStringExtra(MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MEAL_THUMB)!!
    }

    private fun setRecyclerView() {
        binding.moreRecycler.apply {
            layoutManager =
                LinearLayoutManager(this@MealActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mealsOfCategoryAdapter
        }
    }

    private fun observerMealsOfCategoryLiveData() {
        homeViewModel.observeMealsOfCategoryLiveData().observe( this,
            {
                    mealList ->
                mealsOfCategoryAdapter.setMeals(mealsList = mealList as ArrayList<MealX>)

            })
    }

    private fun onItemClick(){
        mealsOfCategoryAdapter.onItemClick = {
            meal ->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }


}