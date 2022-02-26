package com.example.foodie.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodie.databinding.FragmentOwnRecipeDetailsBinding
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.foodie.R
import com.example.foodie.viewmodels.OwnRecipeViewModel


class OwnRecipeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentOwnRecipeDetailsBinding
    private val args by navArgs<OwnRecipeDetailsFragmentArgs>()
    private lateinit var recipeViewModel: OwnRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_ownRecipeDetailsFragment_to_recipesFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnRecipeDetailsBinding.inflate(inflater,container, false)
        setHasOptionsMenu(true)

        recipeViewModel = ViewModelProvider(this).get(OwnRecipeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvOwnRName.text = args.ownRecipe.mealName
        binding.tvOwnRIngredientsList.text = args.ownRecipe.mealIngredients
        binding.tvOwnRInstructions.text = args.ownRecipe.mealInstructions

        binding.editRecipeFloatingButton.setOnClickListener {
            val action = OwnRecipeDetailsFragmentDirections.actionOwnRecipeDetailsFragmentToUpdateOwnRecipeFragment(args.ownRecipe)
            binding.editRecipeFloatingButton.findNavController().navigate(action)
        }


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
            findNavController().navigate(R.id.action_ownRecipeDetailsFragment_to_recipesFragment)
        }
        builder.setNegativeButton("No"){
                _, _ ->

        }
        builder.setTitle("Delete ${args.ownRecipe.mealName}?")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.create().show()
    }


}