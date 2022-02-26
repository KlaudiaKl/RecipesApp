package com.example.foodie.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodie.DB.dataclasses.OwnRecipe
import com.example.foodie.R
import com.example.foodie.databinding.FragmentAddRecipeBinding
import com.example.foodie.viewmodels.OwnRecipeViewModel


class AddRecipeFragment : Fragment() {

    private lateinit var binding: FragmentAddRecipeBinding
    private lateinit var viewModel: OwnRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_addRecipeFragment_to_recipesFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[OwnRecipeViewModel::class.java]

        binding.addRecipeButton.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase(){
        val name = binding.etAddMealName.text.toString()
        val category = binding.etAddCategoryName.text.toString()
        val ingredients = binding.etAddIngredients.text.toString()
        val instructions = binding.etAddInstructions.text.toString()

        if (inputCheck(name, category, ingredients, instructions)){
            val recipe = OwnRecipe(0,name, category, ingredients, instructions)
            viewModel.addRecipe(recipe)
            Toast.makeText(requireContext(), "Added to database", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addRecipeFragment_to_recipesFragment)
        }
        else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String, category:String, ingredients: String, instructions: String) : Boolean{
        return!(TextUtils.isEmpty(name)&&TextUtils.isEmpty(category)&&TextUtils.isEmpty(ingredients)&&TextUtils.isEmpty(instructions))
    }

}