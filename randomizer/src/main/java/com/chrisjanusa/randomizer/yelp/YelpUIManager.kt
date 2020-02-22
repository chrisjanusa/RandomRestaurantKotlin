package com.chrisjanusa.randomizer.yelp

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.deeplinks.actions.GoogleMapsClickAction
import com.chrisjanusa.randomizer.deeplinks.actions.UberClickAction
import com.chrisjanusa.randomizer.deeplinks.actions.YelpClickAction
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.metersToMiles
import com.chrisjanusa.randomizer.yelp.actions.RandomizeAction
import com.chrisjanusa.yelp.models.Category
import com.chrisjanusa.yelp.models.Restaurant
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.bottom_overlay.*

object YelpUIManager : FeatureUIManager {
    private const val delimiter = " | "

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.random.setOnClickListener { sendAction(RandomizeAction(), randomizerViewModel) }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.activity?.run {
            if (state.currRestaurant != null) {
                renderCard(state.currRestaurant, fragment)
                findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.card_layout).visibility = View.VISIBLE

            } else {
                findViewById<ConstraintLayout>(R.id.card_layout).visibility = View.GONE
                findViewById<ShimmerFrameLayout>(R.id.shimmer_card_layout).visibility = View.VISIBLE
            }
        }
    }

    private fun renderCard(restaurant: Restaurant, fragment: RandomizerFragment) {
        fragment.activity?.run {
            Glide.with(fragment)
                .load(restaurant.image_url)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(findViewById(R.id.thumbnail))
            findViewById<TextView>(R.id.name).text = restaurant.name
            val rating = restaurant.rating ?: 0f
            findViewById<TextView>(R.id.rating).text = "%.1f".format(rating)
            findViewById<RatingBar>(R.id.stars).rating = rating
            findViewById<TextView>(R.id.count).text = restaurant.review_count.toString()
            findViewById<TextView>(R.id.card_cuisines).text = categoriesToDisplayString(restaurant.categories)
            findViewById<TextView>(R.id.distancePrice).text = restaurantToPriceDistanceString(restaurant)
            findViewById<MaterialButton>(R.id.maps).setOnClickListener { sendAction(GoogleMapsClickAction(restaurant.name, restaurant.location), fragment.randomizerViewModel)}
            findViewById<MaterialButton>(R.id.uber).setOnClickListener { sendAction(UberClickAction(restaurant.name, restaurant.location, restaurant.coordinates), fragment.randomizerViewModel)}
            findViewById<MaterialButton>(R.id.yelp).setOnClickListener { sendAction(YelpClickAction(restaurant.url), fragment.randomizerViewModel)}
        }
    }

    private fun categoriesToDisplayString(categories: List<Category>): String {
        val displayString = StringBuilder()
        for (category in categories) {
            displayString.append(category.title)
            displayString.append(", ")
        }
        return displayString.dropLast(2).toString()
    }

    private fun restaurantToPriceDistanceString(restaurant: Restaurant): String {
        val priceDistanceString = StringBuilder()
        restaurant.price?.let { priceDistanceString.append("$it$delimiter") }
        restaurant.distance?.let { priceDistanceString.append("%.2f mi away$delimiter".format(metersToMiles(it))) }
        return priceDistanceString.dropLast(3).toString()
    }
}