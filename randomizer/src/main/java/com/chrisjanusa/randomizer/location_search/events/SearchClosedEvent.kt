package com.chrisjanusa.randomizer.location_search.events

import android.view.View
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.RandomizerFragmentDetails

class SearchClosedEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        (fragment.getFragmentDetails() as? RandomizerFragmentDetails)?.binding?.run {
            locationContainer.searchCard.userInput.setText("")
            val searchShade = locationContainer.searchShade.root
            searchShade.animate()
                .alpha(0f)
                .setDuration(150)
                .withEndAction { searchShade.visibility = View.GONE }
        }
    }
}