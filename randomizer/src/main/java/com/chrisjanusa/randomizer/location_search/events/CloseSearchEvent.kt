package com.chrisjanusa.randomizer.location_search.events

import android.content.Context
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.location_search.SearchHelper

class CloseSearchEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.run {
            // Remove focus from search and close keyboard
            // TODO: Synthetics
//            user_input.clearFocus()
//            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//            imm?.hideSoftInputFromWindow(user_input.windowToken, 0)

            // Set the layout for default style
            val layoutParams = ConstraintLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.location_search_default_width),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.startToEnd = R.id.search_icon

            // Set style of search bar back to default
//            user_input.layoutParams = layoutParams
//            SearchHelper.removeView(back_icon)
//            SearchHelper.addView(search_icon)
//            SearchHelper.addView(gps_button)
//            SearchHelper.addView(divider_line)
//            SearchHelper.addView(current_location)
        }
    }

}