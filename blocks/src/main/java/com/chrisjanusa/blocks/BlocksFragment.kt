package com.chrisjanusa.blocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.blocks.databinding.BlocksFragBinding

class BlocksFragment : BaseRestaurantFragment() {
    private var _binding: BlocksFragBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun getFeatureUIManagers() = listOf(BlocksListUIManager)

    override fun getFragmentDetails(): BaseFragmentDetails {
        return BlocksFragmentDetails(this, binding)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BlocksFragBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}