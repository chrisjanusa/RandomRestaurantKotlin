package com.chrisjanusa.randomizer.yelp.events

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.bottom_overlay.*

class FinishedLoadingNewRestaurantsEvent : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        fragment.activity?.run{
            findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).visibility = View.GONE
            findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).stopShimmer()
            findViewById<ConstraintLayout>(R.id.card_layout).visibility = View.VISIBLE
            random.visibility = View.VISIBLE
            shimmer_random.visibility = View.GONE
            shimmer_random.stopShimmer()
        }
    }
}