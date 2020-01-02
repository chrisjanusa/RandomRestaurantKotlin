package com.chrisjanusa.randomizer.filter_base

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.filter_category.CategoryFragment
import com.chrisjanusa.randomizer.filter_distance.DistanceFragment
import com.chrisjanusa.randomizer.filter_price.PriceFragment
import com.chrisjanusa.randomizer.filter_restriction.RestrictionFragment
import com.chrisjanusa.randomizer.base.ActionHelper
import com.chrisjanusa.randomizer.filter_base.actions.CancelClickAction
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
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


    sealed class Filter(val fragment: Fragment?) {
        object None : Filter(null)
        object Price : Filter(PriceFragment())
        object Category : Filter(CategoryFragment())
        object Distance : Filter(DistanceFragment())
        object Restriction : Filter(RestrictionFragment())
    }
}