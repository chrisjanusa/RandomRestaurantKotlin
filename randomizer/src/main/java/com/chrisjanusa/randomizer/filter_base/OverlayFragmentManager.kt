package com.chrisjanusa.randomizer.filter_base

import androidx.fragment.app.Fragment

interface OverlayFragmentManager {
    fun onFilterSelected(fragment: Fragment)
    fun onFilterClosed()
}