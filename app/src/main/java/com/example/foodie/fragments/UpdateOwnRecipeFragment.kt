package com.example.foodie.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodie.DB.dataclasses.OwnRecipe
import com.example.foodie.R
import com.example.foodie.databinding.FragmentUpdateOwnRecipeBinding
import com.example.foodie.viewmodels.OwnRecipeViewModel


class UpdateOwnRecipeFragment : Fragment() {
    private lateinit var binding : FragmentUpdateOwnRecipeBinding
    private val args by navArgs<UpdateOwnRecipeFragmentArgs>()
    private lateinit var recipeViewModel: OwnRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_updateOwnRecipeFragment_to_recipesFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateOwnRecipeBinding.inflate(inflater, container, false)
        recipeViewModel = ViewModelProvider(this).get(OwnRecipeViewModel::class.java)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etEditMealName.setText(args.ownRecipe.mealName)
        binding.etEditCategoryName.setText(args.ownRecipe.mealCategory)
        binding.etEditIngredients.setText(args.ownRecipe.mealIngredients)
        binding.etEditInstructions.setText(args.ownRecipe.mealInstructions)

        binding.updateRecipeButton.setOnClickListener {
            updateRecipe()
        }
    }

    private fun updateRecipe(){
        val recipeName = binding.etEditMealName.text.toString()
        val recipeCategory = binding.etEditCategoryName.text.toString()
        val recipeIngredients = binding.etEditIngredients.text.toString()
        val recipeInstructions = binding.etEditInstructions.text.toString()

        if(inputCheck(recipeName, recipeCategory, recipeIngredients, recipeInstructions)){
            val updatedRecipe = OwnRecipe(args.ownRecipe.id, recipeName, recipeCategory, recipeIngredients, recipeInstructions)
            recipeViewModel.updateRecipe(updatedRecipe)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateOwnRecipeFragment_to_recipesFragment)

        }
        else{
            Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String, category:String, ingredients: String, instructions: String) : Boolean{
        return!(TextUtils.isEmpty(name)&& TextUtils.isEmpty(category)&& TextUtils.isEmpty(ingredients)&& TextUtils.isEmpty(instructions))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteRecipe()
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteRecipe(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){
            _, _->
            recipeViewModel.deleteRecipe(args.ownRecipe)
            Toast.makeText(requireContext(), "Recipe deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateOwnRecipeFragment_to_recipesFragment)
        }
        builder.setNegativeButton("No"){
            _, _ ->

        }
        builder.setTitle("Delete ${args.ownRecipe.mealName}?")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.create().show()
    }

}