package com.chrisjanusa.randomizer.search

import android.view.View

object SearchHelper {
    fun removeView(v: View) {
        v.visibility = View.GONE
    }

    fun addView(v: View) {
        v.visibility = View.VISIBLE
    }
}