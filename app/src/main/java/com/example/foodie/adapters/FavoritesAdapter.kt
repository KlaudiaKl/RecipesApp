package com.example.foodie.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.DB.dataclasses.Favorites
import com.example.foodie.databinding.ItemSearchResultBinding

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavouritesViewHolder>() {

    private var favouritesList = emptyList<Favorites>()

    lateinit var onItemClick: ((Favorites) -> Unit)

    class FavouritesViewHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root){

    }

    fun setFavouritesList(favourite: List<Favorites>){
        this.favouritesList = favourite
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        return FavouritesViewHolder(ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(favouritesList[position].mealThumb).into(holder.binding.searchedItemImg)
        holder.binding.searchedItemName.text = favouritesList[position].mealName

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(favouritesList[position])
        }
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    fun getFavourite(position: Int): Favorites {
        return favouritesList[position]
        notifyDataSetChanged()
    }


}