package com.chrisjanusa.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.baselist.BaseRestaurantViewHolder
import com.chrisjanusa.favorites.FavoritesListUIManager.renderFavBlock
import com.chrisjanusa.restaurant.Restaurant

class RestaurantViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    BaseRestaurantViewHolder(inflater, parent) {

    override fun bind(
        restaurant: Restaurant,
        randomizerViewModel: RandomizerViewModel
    ) {
        super.bind(restaurant, randomizerViewModel)
        renderFavBlock(restaurant, itemView, randomizerViewModel, adapterPosition)
    }

}