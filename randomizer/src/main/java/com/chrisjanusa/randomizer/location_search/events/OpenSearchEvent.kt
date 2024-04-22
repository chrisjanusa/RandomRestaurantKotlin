package com.chrisjanusa.randomizer.location_search.events

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.location_search.SearchHelper

class OpenSearchEvent(private val addressSearchText: String) : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        (fragment.getFragmentDetails() as? RandomizerFragmentDetails)?.binding?.locationContainer?.searchCard?.run {
            // Setup the layout
            val layoutParams = ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.startToEnd = R.id.back_icon
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.matchConstraintDefaultWidth = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD
            userInput.layoutParams = layoutParams

            // Have the correct views showing
            SearchHelper.addView(backIcon)
            SearchHelper.removeView(searchIcon)
            SearchHelper.removeView(gpsButton)
            SearchHelper.removeView(dividerLine)
            SearchHelper.removeView(currentLocation)

            // Set the text to the last query and highlight it
            userInput.setText(addressSearchText)
            userInput.selectAll()
        }
    }
}