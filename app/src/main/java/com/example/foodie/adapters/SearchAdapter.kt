package com.example.foodie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.databinding.ItemSearchResultBinding
import com.example.foodie.models.Meal

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MealsViewHolder>() {

    lateinit var onItemClick: ((Meal) -> Unit)

    var mealsList = ArrayList<Meal>()

    fun setMealsList(mealsList: List<Meal>){
        this.mealsList = mealsList as ArrayList<Meal>
        notifyDataSetChanged()

    }

    class MealsViewHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.searchedItemImg)
        holder.binding.searchedItemName.text = mealsList[position].strMeal
        holder.binding.searchedItemCategory.text = mealsList[position].strCategory
        holder.binding.searchedItemArea.text = mealsList[position].strArea

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}