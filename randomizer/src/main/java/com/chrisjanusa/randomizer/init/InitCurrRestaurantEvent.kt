package com.chrisjanusa.randomizer.init

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.yelp.YelpUIManager.renderCard
import com.chrisjanusa.yelp.models.Restaurant
import com.facebook.shimmer.ShimmerFrameLayout

class InitCurrRestaurantEvent(
    private val currRestaurant: Restaurant?,
    private val randomizerState: RandomizerState,
    private val activity: FragmentActivity
) : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        if (currRestaurant == null) {
            activity.findViewById<ConstraintLayout>(R.id.card_layout).visibility = View.GONE
            activity.findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).visibility = View.VISIBLE
            return
        }

        activity.findViewById<ImageView>(R.id.thumbnail)?.let {
            Glide.with(activity)
                .load(currRestaurant.image_url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(it)
        }
        randomizerState.currRestaurant?.let {
            renderCard(it, randomizerState, activity)
        }
        activity.findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).visibility = View.GONE
        activity.findViewById<ConstraintLayout>(R.id.card_layout).visibility = View.VISIBLE
    }
}