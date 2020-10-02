package com.chrisjanusa.randomizer.location_search.events

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.base.interfaces.BaseEvent
import kotlinx.android.synthetic.main.search_card.*

class SearchOpenedEvent : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        fragment.run {
            user_input.requestFocus()
            user_input.showDropDown()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(user_input, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}