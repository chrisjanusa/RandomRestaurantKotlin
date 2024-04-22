package com.chrisjanusa.blocks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chrisjanusa.base.CommunicationHelper
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.baselist.BlockClickAndRefreshAction
import com.chrisjanusa.baselist.FavoriteClickAndRefreshAction
import com.chrisjanusa.baselist.RestaurantAdapter
import com.chrisjanusa.yelp.models.Restaurant
import java.util.*

object BlocksListUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is BlocksFragmentDetails) {
            baseFragmentDetails.fragment.run {
                baseFragmentDetails.binding.recyclerView.apply {
                    // set a LinearLayoutManager to handle Android
                    // RecyclerView behavior
                    layoutManager = LinearLayoutManager(activity)
                    // set the custom adapter to the RecyclerView
                    adapter = RestaurantAdapter(
                        randomizerViewModel.state.value?.blockList ?: LinkedList(),
                        randomizerViewModel
                    ) { inflater: LayoutInflater, parent: ViewGroup -> RestaurantViewHolder(inflater, parent) }
                }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {

    }

    fun renderFavBlock(
        restaurant: Restaurant,
        view: View,
        randomizerViewModel: RandomizerViewModel,
        position: Int
    ) {
        val state = randomizerViewModel.state.value?.copy() ?: return
        view.run {
            val favIcon =
                if (state.favList.contains(restaurant))
                    R.drawable.star_selected
                else
                    R.drawable.star_default
            findViewById<ImageView>(R.id.favButton).setImageResource(favIcon)
            findViewById<ImageView>(R.id.favButton).setOnClickListener {
                CommunicationHelper.sendAction(
                    FavoriteClickAndRefreshAction(
                        restaurant,
                        position,
                        R.id.recyclerView
                    ),
                    randomizerViewModel
                )
            }

            val blockIcon =
                if (state.blockList.contains(restaurant))
                    R.drawable.block_selected
                else
                    R.drawable.block_default
            findViewById<ImageView>(R.id.blockButton).setImageResource(blockIcon)
            findViewById<ImageView>(R.id.blockButton).setOnClickListener {
                CommunicationHelper.sendAction(
                    BlockClickAndRefreshAction(
                        restaurant,
                        position,
                        R.id.recyclerView
                    ),
                    randomizerViewModel
                )
            }
        }
    }

}