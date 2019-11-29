package com.chrisjanusa.randomizer.filter_fragments

import androidx.fragment.app.Fragment

interface OverlayFragmentManager {
    fun onFilterSelected(fragment: Fragment)
    fun onFilterClosed()
}