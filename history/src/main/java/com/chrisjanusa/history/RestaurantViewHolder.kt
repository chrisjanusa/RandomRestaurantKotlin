package com.chrisjanusa.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.history.HistoryListUIManager.renderCard
import com.chrisjanusa.yelp.models.Restaurant

class RestaurantViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.card_wrapper, parent, false)) {

    fun bind(
        restaurant: Restaurant,
        randomizerViewModel: RandomizerViewModel
    ) {
        renderCard(restaurant, itemView, randomizerViewModel, adapterPosition)
    }

}