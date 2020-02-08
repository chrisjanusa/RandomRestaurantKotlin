package com.chrisjanusa.randomizer.filter_base

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.actions.CancelClickAction
import com.chrisjanusa.randomizer.filter_base.actions.ClickSelectionFilterAction
import com.chrisjanusa.randomizer.filter_cuisine.CuisineFragment
import com.chrisjanusa.randomizer.filter_distance.DistanceFragment
import com.chrisjanusa.randomizer.filter_price.PriceFragment
import com.chrisjanusa.randomizer.filter_diet.DietFragment
import com.google.android.material.button.MaterialButton

object FilterHelper {
    fun onCancelFilterClick(randomizerViewModel: RandomizerViewModel) {
        sendAction(CancelClickAction(), randomizerViewModel)
    }

    fun clickSelectionFilter(filter: Filter, randomizerViewModel: RandomizerViewModel) {
        sendAction(ClickSelectionFilterAction(filter), randomizerViewModel)
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
        } else {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_not_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_not_selected))
            button.setIconTintResource(R.color.filter_text_not_selected)
            button.setStrokeColorResource(R.color.outline)
        }
    }


    sealed class Filter(val fragment: Fragment) {
        object Price : Filter(PriceFragment())
        object Cuisine : Filter(CuisineFragment())
        object Distance : Filter(DistanceFragment())
        object Diet : Filter(DietFragment())
    }
}