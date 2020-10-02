package com.chrisjanusa.randomizer.yelp.events

import android.view.View
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.bottom_overlay.*

class StartLoadingNewRestaurantsEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.activity?.run{
            findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).startShimmer()
            shimmer_random.startShimmer()
            shimmer_random.visibility = View.VISIBLE
            random.visibility = View.GONE
        }
    }
}