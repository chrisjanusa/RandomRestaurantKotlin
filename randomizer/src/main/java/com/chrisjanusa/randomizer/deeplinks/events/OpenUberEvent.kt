package com.chrisjanusa.randomizer.deeplinks.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.BuildConfig.uberClientId
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.deeplinks.DeeplinkHelper.encodeUrl
import com.chrisjanusa.randomizer.deeplinks.DeeplinkHelper.openDeeplink
import com.chrisjanusa.yelp.models.Coordinates
import com.chrisjanusa.yelp.models.Location

class OpenUberEvent(private val name: String, private val location: Location, private val coordinates: Coordinates) : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        val formattedAddress = encodeUrl("${location.address1}, ${location.city}, ${location.state}")
        val nickname = encodeUrl(name)
        val url = "uber://?client_id=$uberClientId&action=setPickup&dropoff[latitude]=${coordinates.latitude}l&dropoff[longitude]=${coordinates.longitude}&dropoff[nickname]=$nickname&dropoff[formatted_address]=$formattedAddress"
        fragment.context?.let{context ->
            openDeeplink(url, context)
        }
    }
}