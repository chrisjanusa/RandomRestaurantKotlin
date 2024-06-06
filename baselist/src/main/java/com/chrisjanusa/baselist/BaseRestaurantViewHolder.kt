package com.chrisjanusa.baselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.restaurant.Restaurant

open class BaseRestaurantViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.card_wrapper, parent, false)) {

    open fun bind(
        restaurant: Restaurant,
        randomizerViewModel: RandomizerViewModel
    ) {
        renderListCardDetails(restaurant, itemView, randomizerViewModel)
    }

}