package com.chrisjanusa.randomizer.yelp

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.chrisjanusa.base.CommunicationHelper
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.yelp.actions.RandomizeAction
import com.chrisjanusa.restaurant_base.categoriesToDisplayString
import com.chrisjanusa.restaurant_base.deeplinks.actions.GoogleMapsClickAction
import com.chrisjanusa.restaurant_base.deeplinks.actions.UberClickAction
import com.chrisjanusa.restaurant_base.deeplinks.actions.YelpClickAction
import com.chrisjanusa.restaurant_base.restaurantToPriceDistanceString
import com.chrisjanusa.restaurant_base.restaurant_block.actions.BlockClickAction
import com.chrisjanusa.restaurant_base.restaurant_favorite.actions.FavoriteClickAction
import com.chrisjanusa.yelp.models.Restaurant
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.bottom_overlay.*

object YelpUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: BaseRestaurantFragment) {
        fragment.random.setOnClickListener { sendAction(RandomizeAction(), randomizerViewModel) }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {
        fragment.activity?.run {
            val restaurant = state.currRestaurant
            if (restaurant != null) {
                renderCard(restaurant, state, this)
                findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.card_layout).visibility = View.VISIBLE

            } else {
                findViewById<ConstraintLayout>(R.id.card_layout).visibility = View.GONE
                findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).visibility = View.VISIBLE
            }
        }
    }

    fun renderCard(
        restaurant: Restaurant,
        state: RandomizerState,
        fragActivity: FragmentActivity
    ) {
        val randomizerViewModel = CommunicationHelper.getViewModel(fragActivity)
        fragActivity.run {
            findViewById<TextView>(com.chrisjanusa.base.R.id.name).text = restaurant.name
            val rating = restaurant.rating ?: 0f
            findViewById<TextView>(com.chrisjanusa.base.R.id.rating).text = "%.1f".format(rating)
            findViewById<RatingBar>(com.chrisjanusa.base.R.id.stars).rating = rating
            findViewById<TextView>(com.chrisjanusa.base.R.id.count).text =
                restaurant.review_count.toString()
            findViewById<TextView>(com.chrisjanusa.base.R.id.card_cuisines).text =
                categoriesToDisplayString(restaurant.categories)
            findViewById<TextView>(com.chrisjanusa.base.R.id.distancePrice).text =
                restaurantToPriceDistanceString(restaurant)

            val favIcon =
                if (state.favSet.contains(restaurant))
                    com.chrisjanusa.base.R.drawable.star_selected
                else
                    com.chrisjanusa.base.R.drawable.star_default
            findViewById<ImageView>(com.chrisjanusa.base.R.id.favButton).setImageResource(favIcon)
            findViewById<ImageView>(com.chrisjanusa.base.R.id.favButton).setOnClickListener {
                sendAction(
                    FavoriteClickAction(restaurant),
                    randomizerViewModel
                )
            }

            val blockIcon =
                if (state.blockSet.contains(restaurant))
                    com.chrisjanusa.base.R.drawable.block_selected
                else
                    com.chrisjanusa.base.R.drawable.block_default
            findViewById<ImageView>(com.chrisjanusa.base.R.id.blockButton).setImageResource(
                blockIcon
            )
            findViewById<ImageView>(com.chrisjanusa.base.R.id.blockButton).setOnClickListener {
                sendAction(
                    BlockClickAction(restaurant),
                    randomizerViewModel
                )
            }

            findViewById<MaterialButton>(com.chrisjanusa.base.R.id.maps).setOnClickListener {
                sendAction(
                    GoogleMapsClickAction(
                        restaurant.name,
                        restaurant.location
                    ), randomizerViewModel
                )
            }
            val uberClickAction =
                UberClickAction(restaurant.name, restaurant.location, restaurant.coordinates)
            findViewById<MaterialButton>(com.chrisjanusa.base.R.id.uber).setOnClickListener {
                sendAction(uberClickAction, randomizerViewModel)
            }
            findViewById<MaterialButton>(com.chrisjanusa.base.R.id.yelp).setOnClickListener {
                sendAction(
                    YelpClickAction(restaurant.url),
                    randomizerViewModel
                )
            }

        }
    }
}