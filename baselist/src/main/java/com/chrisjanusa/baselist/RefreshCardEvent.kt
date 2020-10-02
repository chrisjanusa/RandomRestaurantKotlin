package com.chrisjanusa.baselist

import androidx.recyclerview.widget.RecyclerView
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment

class RefreshCardEvent(private val position: Int, private val recyclerViewId: Int) : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.activity?.findViewById<RecyclerView>(recyclerViewId).let {
            it?.adapter?.notifyItemChanged(position)
        }
    }
}