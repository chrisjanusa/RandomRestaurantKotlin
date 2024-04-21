package com.chrisjanusa.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.favorites.databinding.FavoritesFragBinding

class FavoritesFragment : BaseRestaurantFragment() {
    private var _binding: FavoritesFragBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun getFeatureUIManagers() = listOf(FavoritesListUIManager)

    override fun getFragmentDetails(): BaseFragmentDetails {
        return FavoritesFragmentDetails(this, binding)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoritesFragBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}