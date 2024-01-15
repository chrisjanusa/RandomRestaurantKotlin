package com.chrisjanusa.randomizer.location_search.events

import android.view.View
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment

class SearchClosedEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.run {
            // TODO: Synthetics
//            user_input.setText("")
//            search_shade.animate()
//                .alpha(0f)
//                .setDuration(150)
//                .withEndAction { search_shade.visibility = View.GONE }
        }
    }
}