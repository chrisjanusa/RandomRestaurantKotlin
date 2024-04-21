package com.chrisjanusa.randomizer.location_search.events

import android.content.Context
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.location_search.SearchHelper

class CloseSearchEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        (fragment.getFragmentDetails() as? RandomizerFragmentDetails)?.binding?.run {
            // Remove focus from search and close keyboard
            val userInput = locationContainer.searchCard.userInput
            userInput.clearFocus()
            val imm = fragment.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(userInput.windowToken, 0)

            // Set the layout for default style
            val layoutParams = ConstraintLayout.LayoutParams(
                fragment.resources.getDimensionPixelSize(R.dimen.location_search_default_width),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.startToEnd = R.id.search_icon

            // Set style of search bar back to default
            userInput.layoutParams = layoutParams
            locationContainer.searchCard.run {
                SearchHelper.removeView(backIcon)
                SearchHelper.addView(searchIcon)
                SearchHelper.addView(gpsButton)
                SearchHelper.addView(dividerLine)
                SearchHelper.addView(currentLocation)
            }
        }
    }

}