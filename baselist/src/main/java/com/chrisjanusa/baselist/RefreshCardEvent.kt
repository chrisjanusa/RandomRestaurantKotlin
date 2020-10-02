package com.chrisjanusa.baselist

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.chrisjanusa.base.interfaces.BaseEvent

class RefreshCardEvent(private val position: Int, private val recyclerViewId: Int) : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        fragment.activity?.findViewById<RecyclerView>(recyclerViewId).let {
            it?.adapter?.notifyItemChanged(position)
        }
    }
}