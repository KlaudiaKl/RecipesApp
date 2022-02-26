package com.example.foodie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodie.DB.dataclasses.OwnRecipe
import com.example.foodie.databinding.OwnRecipeCardBinding
import com.example.foodie.fragments.RecipesFragmentDirections

class OwnRecipesAdapter: RecyclerView.Adapter<OwnRecipesAdapter.ViewHolder>() {

    private var ownRecipeList = emptyList<OwnRecipe>()
    //lateinit var onClick : ((OwnRecipe) -> Unit)

    fun setRecipeList(recipe: List<OwnRecipe>){
        this.ownRecipeList = recipe
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: OwnRecipeCardBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OwnRecipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ownMealName.text = ownRecipeList[position].mealName
        holder.binding.ownMealCategory.text = "Category: ${ownRecipeList[position].mealCategory}"

        holder.itemView.setOnClickListener {
            //onClick.invoke(ownRecipeList[position])
            val action = RecipesFragmentDirections.actionRecipesFragmentToOwnRecipeDetailsFragment(ownRecipeList[position])
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return ownRecipeList.size
    }
}