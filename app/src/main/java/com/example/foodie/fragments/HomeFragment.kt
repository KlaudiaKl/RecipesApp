package com.example.foodie.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodie.CategoryDetailsActivity
import com.example.foodie.MealActivity
import com.example.foodie.R
import com.example.foodie.adapters.CategoriesAdapter
import com.example.foodie.adapters.PopularMealsAdapter
import com.example.foodie.databinding.FragmentHomeBinding
import com.example.foodie.models.CategoryX
import com.example.foodie.models.Meal
import com.example.foodie.models.MealX
import com.example.foodie.viewmodels.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding :  FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var mealsOfCategoryAdapter: PopularMealsAdapter
    private lateinit var categoryAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mealsOfCategoryAdapter = PopularMealsAdapter()
        categoryAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        homeViewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeViewModel.getMealsOfCategory("Seafood")
        observerMealsOfCategoryLiveData()
        onItemClick()

        homeViewModel.getCategories()
        observerCategoryLiveData()
        onCategoryClick()

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }



    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = {
            category ->
            val intent = Intent(activity, CategoryDetailsActivity::class.java)
            intent.putExtra(MealActivity.CATEGORY_NAME, category.strCategory)
            intent.putExtra(MealActivity.CATEGORY_DESCRIPTION, category.strCategoryDescription)
            startActivity(intent)
        }
    }

    fun onItemClick() {
        mealsOfCategoryAdapter.onItemClick = {
            meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MealActivity.MEAL_ID, meal.idMeal)
            intent.putExtra(MealActivity.MEAL_NAME, meal.strMeal)
            intent.putExtra(MealActivity.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setRecyclerView() {
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mealsOfCategoryAdapter
        }

        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observerMealsOfCategoryLiveData() {
        homeViewModel.observeMealsOfCategoryLiveData().observe(viewLifecycleOwner,
             {
                mealList ->
                    mealsOfCategoryAdapter.setMeals(mealsList = mealList as ArrayList<MealX>)
                 })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MealActivity.MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MealActivity.MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MealActivity.MEAL_THUMB, randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeViewModel.observeRandomMealLivedata().observe(viewLifecycleOwner, {
            meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imageRandomMeal)

            this.randomMeal = meal

        })
    }

    private fun observerCategoryLiveData(){
        homeViewModel.observeCategoryLiveData().observe(viewLifecycleOwner,{
            categoryList
            ->
                categoryAdapter.setCategoryList(categoryList as ArrayList<CategoryX>)
        })
    }
}