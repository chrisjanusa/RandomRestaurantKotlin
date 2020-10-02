package com.chrisjanusa.randomizer.yelp.events

import android.view.View
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.base.interfaces.BaseEvent
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.bottom_overlay.*

class FinishedLoadingNewRestaurantsEvent : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        fragment.activity?.run{
            findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).stopShimmer()
            random.visibility = View.VISIBLE
            shimmer_random.visibility = View.GONE
            shimmer_random.stopShimmer()
        }
    }
}