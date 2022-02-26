package com.example.foodie.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodie.DB.dataclasses.Favorites
import com.example.foodie.MealActivity
import com.example.foodie.adapters.FavoritesAdapter
import com.example.foodie.databinding.FragmentFavouritesBinding
import com.example.foodie.viewmodels.FavoritesViewModel
import com.google.android.material.snackbar.Snackbar


class FavouritesFragment() : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favAdapter : FavoritesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favAdapter = FavoritesAdapter()
        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        //setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        onFavoriteClick()
        favoritesViewModel.readAllData.observe(this, Observer{
                favorite ->
            favAdapter.setFavouritesList(favorite as List<Favorites>)
            Log.i("favs", favorite.toString())

        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val fav = favAdapter.getFavourite(pos)
                favoritesViewModel.deleteFavorite(fav)
                Snackbar.make(requireView(), "Item removed", Snackbar.LENGTH_LONG).setAction("UNDO"){
                    favoritesViewModel.addFavorite(fav)
                }.show()


            }

        }).attachToRecyclerView(binding.favoritesRv)


    }


    private fun setRecyclerView() {
        binding.favoritesRv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }
    }

    private fun onFavoriteClick(){
        favAdapter.onItemClick = {
            favorite ->
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra(MealActivity.MEAL_ID, favorite.mealId.toString())
            intent.putExtra(MealActivity.MEAL_NAME, favorite.mealName)
            intent.putExtra(MealActivity.MEAL_THUMB, favorite.mealThumb)
            startActivity(intent)
        }
    }




}