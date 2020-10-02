package com.chrisjanusa.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.yelp.models.Restaurant

class RestaurantAdapter(
    private val list: List<Restaurant>,
    private val randomizerViewModel: RandomizerViewModel
) : RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RestaurantViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant: Restaurant = list.elementAt(position)
        holder.bind(restaurant, randomizerViewModel)
    }

    override fun getItemCount(): Int = list.size
}