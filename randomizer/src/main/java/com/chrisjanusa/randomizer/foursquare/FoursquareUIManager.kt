package com.chrisjanusa.randomizer.foursquare

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.chrisjanusa.base.CommunicationHelper
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.foursquare.actions.RandomizeAction
import com.chrisjanusa.restaurant_base.restaurant_report.actions.ReportClickAction
import com.chrisjanusa.restaurant.Restaurant
import com.chrisjanusa.restaurant_base.categoriesToDisplayString
import com.chrisjanusa.restaurant_base.deeplinks.actions.GoogleMapsClickAction
import com.chrisjanusa.restaurant_base.deeplinks.actions.UberClickAction
import com.chrisjanusa.restaurant_base.deeplinks.actions.LinkClickAction
import com.chrisjanusa.restaurant_base.restaurantToPriceDistanceString
import com.chrisjanusa.restaurant_base.restaurant_block.actions.BlockClickAction
import com.chrisjanusa.restaurant_base.restaurant_favorite.actions.FavoriteClickAction
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton

object FoursquareUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                baseFragmentDetails.binding.restaurantContainer.random.setOnClickListener { sendAction(RandomizeAction(), randomizerViewModel) }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.activity?.run {
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
                restaurant.ratingCount.toString()
            findViewById<TextView>(com.chrisjanusa.base.R.id.card_cuisines).text =
                categoriesToDisplayString(restaurant.categories)
            findViewById<TextView>(com.chrisjanusa.base.R.id.distancePrice).text =
                restaurantToPriceDistanceString(restaurant, true)

            val favIcon =
                if (state.favList.contains(restaurant))
                    com.chrisjanusa.base.R.drawable.star_selected
                else
                    com.chrisjanusa.base.R.drawable.star_default
            findViewById<ImageView>(com.chrisjanusa.base.R.id.favButton).setImageResource(favIcon)
            findViewById<RelativeLayout>(com.chrisjanusa.base.R.id.favButtonBox).setOnClickListener {
                sendAction(
                    FavoriteClickAction(restaurant),
                    randomizerViewModel
                )
            }

            val blockIcon =
                if (state.blockList.contains(restaurant))
                    com.chrisjanusa.base.R.drawable.block_selected
                else
                    com.chrisjanusa.base.R.drawable.block_default
            findViewById<ImageView>(com.chrisjanusa.base.R.id.blockButton).setImageResource(
                blockIcon
            )
            findViewById<RelativeLayout>(com.chrisjanusa.base.R.id.blockButtonBox).setOnClickListener {
                sendAction(
                    BlockClickAction(restaurant),
                    randomizerViewModel
                )
            }

            findViewById<RelativeLayout>(com.chrisjanusa.base.R.id.reportButtonBox).setOnClickListener {
                sendAction(
                    ReportClickAction(restaurant),
                    randomizerViewModel
                )
            }

            val reportIcon =
                if (!state.reportMap[restaurant.id].isNullOrBlank())
                    R.drawable.report_selected
                else
                    R.drawable.report
            findViewById<ImageView>(R.id.reportButton).setImageResource(reportIcon)
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
            val websiteButton = findViewById<MaterialButton>(com.chrisjanusa.base.R.id.website)
            websiteButton.setText(restaurant.link.textRes)
            websiteButton.setIconResource(restaurant.link.iconRes)
            websiteButton.setOnClickListener {
                sendAction(
                    LinkClickAction(restaurant.link.url),
                    randomizerViewModel
                )
            }

        }
    }
}