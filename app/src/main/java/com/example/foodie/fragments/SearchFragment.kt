package com.example.foodie.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.MealActivity
import com.example.foodie.R
import com.example.foodie.viewmodels.SearchViewModel
import com.example.foodie.adapters.SearchAdapter
import com.example.foodie.databinding.FragmentSearchBinding
import com.example.foodie.models.Meal


class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private  lateinit var searchViewModel: SearchViewModel
    private lateinit var meal: Meal
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchAdapter = SearchAdapter()
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        //searchViewModel.searchMeals("Pancakes", context)
        observeSearchLiveData()
        onItemClick()



       binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                searchViewModel.searchMeals(query.toString(), context)
                //observeSearchLiveData()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.searchMeals(newText.toString(), context)
                return true
            }

        })
    }

    private fun observeSearchLiveData(){
        searchViewModel.observerSearchLiveData().observe(viewLifecycleOwner,{
            meals ->
            searchAdapter.setMealsList(meals as ArrayList<Meal>)
        })
    }

    private fun setRecyclerView(){
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }

    private fun onItemClick(){
        searchAdapter.onItemClick = {
            meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MealActivity.MEAL_ID, meal.idMeal)
            intent.putExtra(MealActivity.MEAL_NAME, meal.strMeal)
            intent.putExtra(MealActivity.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }


}