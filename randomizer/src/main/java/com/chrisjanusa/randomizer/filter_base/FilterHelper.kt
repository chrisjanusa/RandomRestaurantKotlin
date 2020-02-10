package com.chrisjanusa.randomizer.filter_base

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat.getColor
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

    fun renderFilterOptionStyle(button: MaterialButton, selected: Boolean, context: Context) {
        val backgroundColor = if (selected) R.color.filter_background_selected else R.color.filter_background_not_selected
        val textColor = if (selected) R.color.filter_text_selected else R.color.filter_text_not_selected
        val strokeColor = if (selected) R.color.outline_selected else R.color.outline_not_selected
        renderButtonStyle(backgroundColor, textColor, strokeColor, button, context)
    }

    fun renderFilterStyle(button: MaterialButton, selected: Boolean, context: Context) {
        val backgroundColor = if (selected) R.color.filter_background_selected else R.color.filter_background_not_selected
        val textColor = if (selected) R.color.filter_text_selected else R.color.filter_text_not_selected
        val strokeColor = if (selected) R.color.outline_selected else R.color.outline_not_selected
        renderIconButtonStyle(backgroundColor, textColor, strokeColor, button, context)
    }

    private fun renderButtonStyle(@ColorRes backgroundColor: Int, @ColorRes textColor: Int, @ColorRes strokeColor: Int,
                          button: MaterialButton, context: Context)
    {
        button.setBackgroundColor(getColor(context, backgroundColor))
        button.setTextColor(getColor(context, textColor))
        button.setStrokeColorResource(strokeColor)
    }

    private fun renderIconButtonStyle(@ColorRes backgroundColor: Int, @ColorRes textColor: Int, @ColorRes strokeColor: Int,
                          button: MaterialButton, context: Context)
    {
        renderButtonStyle(backgroundColor, textColor, strokeColor, button, context)
        button.setIconTintResource(textColor)
    }


    sealed class Filter(val fragment: Fragment) {
        object Price : Filter(PriceFragment())
        object Cuisine : Filter(CuisineFragment())
        object Distance : Filter(DistanceFragment())
        object Diet : Filter(DietFragment())
    }
}