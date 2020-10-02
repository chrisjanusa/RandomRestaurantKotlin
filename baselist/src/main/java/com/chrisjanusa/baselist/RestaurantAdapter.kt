package com.chrisjanusa.baselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.yelp.models.Restaurant

class RestaurantAdapter<T : BaseRestaurantViewHolder>(
    private val list: List<Restaurant>,
    private val randomizerViewModel: RandomizerViewModel,
    private val createViewHolder : (inflater: LayoutInflater, parent: ViewGroup) -> T
) : RecyclerView.Adapter<T>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val inflater = LayoutInflater.from(parent.context)
        return createViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val restaurant: Restaurant = list.elementAt(position)
        holder.bind(restaurant, randomizerViewModel)
    }

    override fun getItemCount(): Int = list.size
}