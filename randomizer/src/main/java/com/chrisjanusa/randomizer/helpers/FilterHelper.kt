package com.chrisjanusa.randomizer.helpers

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.actions.filter.CancelClickAction
import com.chrisjanusa.randomizer.filter_fragments.CategoryFragment
import com.chrisjanusa.randomizer.filter_fragments.DistanceFragment
import com.chrisjanusa.randomizer.filter_fragments.PriceFragment
import com.chrisjanusa.randomizer.filter_fragments.RestrictionFragment
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

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