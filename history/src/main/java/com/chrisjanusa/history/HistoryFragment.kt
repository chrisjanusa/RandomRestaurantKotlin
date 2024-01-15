package com.chrisjanusa.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.history.databinding.HistoryFragBinding

class HistoryFragment : BaseRestaurantFragment() {
    private var _binding: HistoryFragBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun getFeatureUIManagers() = listOf(HistoryListUIManager)

    override fun getFragmentDetails(): BaseFragmentDetails {
        return HistoryFragmentDetails(this, binding)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoryFragBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}