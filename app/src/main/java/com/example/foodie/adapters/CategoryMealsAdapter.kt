package com.example.foodie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.databinding.CategoryMealItemBinding
import com.example.foodie.models.MealX

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.MealsViewHolder>() {

    lateinit var onItemClick: ((MealX) -> Unit)

    var mealsList = ArrayList<MealX>()

    fun setMealsList(mealsList: List<MealX>){
        this.mealsList = mealsList as ArrayList<MealX>
        notifyDataSetChanged()

    }

    class MealsViewHolder(val binding: CategoryMealItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(CategoryMealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.categoryMealImg)
        holder.binding.categoryMealName.text = mealsList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(mealsList[position])
        }

    }

    override fun getItemCount(): Int {
return mealsList.size    }
}