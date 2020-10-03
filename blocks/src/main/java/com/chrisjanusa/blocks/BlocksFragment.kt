package com.chrisjanusa.blocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment

class BlocksFragment : BaseRestaurantFragment() {
    override fun getFeatureUIManagers() = listOf(BlocksListUIManager)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.blocks_frag, container, false)
    }

}