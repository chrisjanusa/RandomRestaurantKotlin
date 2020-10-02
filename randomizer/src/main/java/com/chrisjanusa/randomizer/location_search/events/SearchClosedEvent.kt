package com.chrisjanusa.randomizer.location_search.events

import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import kotlinx.android.synthetic.main.search_card.*

class SearchClosedEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.run {
            user_input.setText("")
        }
    }
}