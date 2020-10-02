package com.chrisjanusa.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment

class HistoryFragment : BaseRestaurantFragment() {
    override fun getFeatureUIManagers() = listOf(HistoryListUIManager)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.history_frag, container, false)
    }

}