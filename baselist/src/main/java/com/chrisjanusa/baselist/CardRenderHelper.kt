package com.chrisjanusa.baselist

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chrisjanusa.base.CommunicationHelper
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.restaurant.Restaurant
import com.chrisjanusa.restaurant_base.categoriesToDisplayString
import com.chrisjanusa.restaurant_base.deeplinks.actions.GoogleMapsClickAction
import com.chrisjanusa.restaurant_base.deeplinks.actions.UberClickAction
import com.chrisjanusa.restaurant_base.deeplinks.actions.LinkClickAction
import com.chrisjanusa.restaurant_base.restaurantToPriceDistanceString
import com.google.android.material.button.MaterialButton

fun renderListCardDetails(
    restaurant: Restaurant,
    view: View,
    randomizerViewModel: RandomizerViewModel,
) {
    view.run {
        findViewById<TextView>(R.id.name).text = restaurant.name
        val rating = restaurant.rating ?: 0f
        findViewById<TextView>(R.id.rating).text = "%.1f".format(rating)
        findViewById<RatingBar>(R.id.stars).rating = rating
        findViewById<TextView>(R.id.count).text = restaurant.ratingCount.toString()
        findViewById<TextView>(R.id.card_cuisines).text =
            categoriesToDisplayString(restaurant.categories)
        findViewById<TextView>(R.id.distancePrice).text =
            restaurantToPriceDistanceString(restaurant, false)


        findViewById<MaterialButton>(R.id.maps).setOnClickListener {
            CommunicationHelper.sendAction(
                GoogleMapsClickAction(
                    restaurant.name,
                    restaurant.location
                ), randomizerViewModel
            )
        }
        val uberClickAction =
            UberClickAction(restaurant.name, restaurant.location, restaurant.coordinates)
        findViewById<MaterialButton>(R.id.uber).setOnClickListener {
            CommunicationHelper.sendAction(uberClickAction, randomizerViewModel)
        }
        val websiteButton = findViewById<MaterialButton>(R.id.website)
        websiteButton.setText(restaurant.link.textRes)
        websiteButton.setIconResource(restaurant.link.iconRes)
        websiteButton.setOnClickListener {
            CommunicationHelper.sendAction(
                LinkClickAction(restaurant.link.url),
                randomizerViewModel
            )
        }
        findViewById<ImageView>(R.id.thumbnail)?.let {
            Glide.with(view)
                .load(restaurant.imageUrl)
                .centerCrop()
                .into(it)
        }
    }
}