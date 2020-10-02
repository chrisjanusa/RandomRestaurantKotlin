package com.chrisjanusa.history

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chrisjanusa.base.CommunicationHelper
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.baselist.BlockClickAndRefreshAction
import com.chrisjanusa.baselist.FavoriteClickAndRefreshAction
import com.chrisjanusa.baselist.renderCardDetails
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.android.synthetic.main.history_frag.*
import java.util.*

object HistoryListUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: Fragment) {
        fragment.run {
            recyclerView.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(activity)
                // set the custom adapter to the RecyclerView
                adapter = RestaurantAdapter(
                    randomizerViewModel.state.value?.historyList ?: LinkedList(),
                    randomizerViewModel
                )
            }
        }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {

    }

    private fun renderFavBlock(
        restaurant: Restaurant,
        view: View,
        randomizerViewModel: RandomizerViewModel,
        position: Int
    ) {
        val state = randomizerViewModel.state.value?.copy() ?: return
        view.run {
            val favIcon =
                if (state.favSet.contains(restaurant))
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
                if (state.blockSet.contains(restaurant))
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

    fun renderCard(
        restaurant: Restaurant,
        view: View,
        randomizerViewModel: RandomizerViewModel,
        position: Int
    ) {
        renderCardDetails(restaurant, view, randomizerViewModel)
        renderFavBlock(restaurant, view, randomizerViewModel, position)
    }
}