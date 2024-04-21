package com.chrisjanusa.randomizer.location_search.events

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.RandomizerFragmentDetails

class SearchOpenedEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        (fragment.getFragmentDetails() as? RandomizerFragmentDetails)?.binding?.locationContainer?.run {
            searchCard.userInput.requestFocus()
            searchCard.userInput.showDropDown()
            val imm = fragment.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(searchCard.userInput, InputMethodManager.SHOW_IMPLICIT)
            searchShade.root.visibility = View.VISIBLE
            searchShade.root.animate()
                .alpha(1f)
                .setDuration(150)
        }
    }
}