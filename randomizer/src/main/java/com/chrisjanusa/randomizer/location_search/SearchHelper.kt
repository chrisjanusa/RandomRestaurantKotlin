package com.chrisjanusa.randomizer.location_search

import android.view.View

object SearchHelper {
    fun removeView(v: View) {
        v.visibility = View.GONE
    }

    fun addView(v: View) {
        v.visibility = View.VISIBLE
    }
}