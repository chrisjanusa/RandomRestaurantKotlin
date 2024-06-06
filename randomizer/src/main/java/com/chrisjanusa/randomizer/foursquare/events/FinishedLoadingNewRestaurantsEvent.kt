package com.chrisjanusa.randomizer.foursquare.events

import android.view.View
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.facebook.shimmer.ShimmerFrameLayout

class FinishedLoadingNewRestaurantsEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        (fragment.getFragmentDetails() as? RandomizerFragmentDetails)?.binding?.run {
            fragment.activity?.findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout)?.stopShimmer()

            restaurantContainer.random.visibility = View.VISIBLE
            restaurantContainer.shimmerRandom.visibility = View.GONE
            restaurantContainer.shimmerRandom.stopShimmer()
        }
    }
}