package com.chrisjanusa.randomizer.location_search.events

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import kotlinx.android.synthetic.main.search_card.*
import kotlinx.android.synthetic.main.top_overlay.*

class SearchOpenedEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.run {
            user_input.requestFocus()
            user_input.showDropDown()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(user_input, InputMethodManager.SHOW_IMPLICIT)
            search_shade.visibility = View.VISIBLE
            search_shade.animate()
                .alpha(1f)
                .setDuration(150)
        }
    }
}