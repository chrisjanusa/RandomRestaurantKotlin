package com.chrisjanusa.randomizer.search.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.events.BaseEvent
import kotlinx.android.synthetic.main.search_card.*

class SearchClosedEvent : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        if (fragment is RandomizerFragment){
            fragment.run {
                user_input.setText("")
            }
        }
    }
}