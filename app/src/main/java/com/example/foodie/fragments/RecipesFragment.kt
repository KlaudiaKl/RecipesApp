package com.example.foodie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.R
import com.example.foodie.adapters.OwnRecipesAdapter
import com.example.foodie.databinding.FragmentRecipesBinding
import com.example.foodie.viewmodels.OwnRecipeViewModel


class RecipesFragment : Fragment() {
    private lateinit var binding: FragmentRecipesBinding
    private lateinit var viewModel: OwnRecipeViewModel
    private lateinit var recipesAdapter: OwnRecipesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[OwnRecipeViewModel::class.java]
        recipesAdapter = OwnRecipesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        binding.addRecipeFloatingAb.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_addRecipeFragment)
        }

        viewModel.allRecipes.observe(this, Observer {
            recipe ->
                    recipesAdapter.setRecipeList(recipe)
        })

       // onItemClick()

    }

    private fun setRecyclerView() {
        binding.rvOwnRecipes.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = recipesAdapter
        }
    }

    /*fun onItemClick(){
        recipesAdapter.onClick = {
            item ->
            val intent = Intent(activity, OwnRecipeDetailsActivity::class.java)
            intent.putExtra(OwnRecipeDetailsActivity.OWN_R_ID, item.id.toString())
            intent.putExtra(OwnRecipeDetailsActivity.OWN_R_NAME, item.mealName)
            intent.putExtra(OwnRecipeDetailsActivity.OWN_R_INGREDIENTS, item.mealIngredients)
            intent.putExtra(OwnRecipeDetailsActivity.OWN_R_INSTRUCTIONS, item.mealInstructions)
            startActivity(intent)

        }
    }*/



}