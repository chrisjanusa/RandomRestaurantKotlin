package com.chrisjanusa.base_filters.fragment

import androidx.fragment.app.Fragment

interface OverlayFragmentManager {
    fun onFilterSelected(fragment: Fragment)
    fun onFilterClosed()
}