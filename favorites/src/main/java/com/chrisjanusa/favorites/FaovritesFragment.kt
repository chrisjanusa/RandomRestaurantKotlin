package com.chrisjanusa.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment

class FaovritesFragment : BaseRestaurantFragment() {
    override fun getFeatureUIManagers() = listOf(FavoritesListUIManager)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.favorites_frag, container, false)
    }

}