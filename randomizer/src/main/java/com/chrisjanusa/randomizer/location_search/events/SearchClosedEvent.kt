package com.chrisjanusa.randomizer.location_search.events

import android.view.View
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import kotlinx.android.synthetic.main.search_card.*
import kotlinx.android.synthetic.main.top_overlay.*

class SearchClosedEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.run {
            user_input.setText("")
            search_shade.animate()
                .alpha(0f)
                .setDuration(150)
                .withEndAction { search_shade.visibility = View.GONE }
        }
    }
}