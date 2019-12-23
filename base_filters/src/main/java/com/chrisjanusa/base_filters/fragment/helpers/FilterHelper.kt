package com.chrisjanusa.base_filters.fragment.helpers

import android.content.Context
import androidx.core.content.ContextCompat
import com.chrisjanusa.base_filters.R
import com.chrisjanusa.base_filters.fragment.filters.CancelClickAction
import com.chrisjanusa.base_randomizer.RandomizerViewModel
import com.chrisjanusa.randomizer.helpers.ActionHelper
import com.google.android.material.button.MaterialButton

object FilterHelper {
    fun onCancelFilterClick(randomizerViewModel: RandomizerViewModel){
        ActionHelper.sendAction(CancelClickAction(), randomizerViewModel)
    }

    fun renderButtonStyle(button: MaterialButton, selected: Boolean, context: Context) {
        if (selected) {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_selected))
            button.setStrokeColorResource(R.color.filter_background_selected)
        } else {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_not_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_not_selected))
            button.setStrokeColorResource(R.color.outline)
        }
    }

    fun renderFilterStyle(button: MaterialButton, selected: Boolean, context: Context) {
        if (selected) {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_selected))
            button.setIconTintResource(R.color.filter_text_selected)
            button.setStrokeColorResource(R.color.filter_background_selected)
        }
        else {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_not_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_not_selected))
            button.setIconTintResource(R.color.filter_text_not_selected)
            button.setStrokeColorResource(R.color.outline)
        }
    }
}