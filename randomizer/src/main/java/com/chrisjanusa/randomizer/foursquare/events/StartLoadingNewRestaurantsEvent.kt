package com.chrisjanusa.randomizer.foursquare.events

import android.view.View
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.facebook.shimmer.ShimmerFrameLayout

class StartLoadingNewRestaurantsEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        val fragmentDetails = fragment.getFragmentDetails()
        if (fragmentDetails is RandomizerFragmentDetails) {
            fragment.activity?.findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout)?.startShimmer()

            fragmentDetails.binding.restaurantContainer.shimmerRandom.startShimmer()
            fragmentDetails.binding.restaurantContainer.shimmerRandom.visibility = View.VISIBLE
            fragmentDetails.binding.restaurantContainer.random.visibility = View.GONE
        }
    }
}