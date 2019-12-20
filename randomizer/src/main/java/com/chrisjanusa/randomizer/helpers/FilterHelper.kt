package com.chrisjanusa.randomizer.helpers

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.actions.filter.CancelClickAction
import com.chrisjanusa.randomizer.filter_fragments.DistanceFragment
import com.chrisjanusa.randomizer.filter_fragments.PriceFragment
import com.chrisjanusa.randomizer.filter_fragments.RestrictionFragment
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import com.google.android.material.button.MaterialButton

object FilterHelper {

    fun getFilterFragment(filter: Filter) : Fragment? {
        return when(filter) {
            is Filter.Price -> PriceFragment()
            is Filter.Category -> null
            is Filter.Distance -> DistanceFragment()
            is Filter.Restriction -> RestrictionFragment()
            is Filter.None -> null
        }
    }

    fun onCancelFilterClick(randomizerViewModel: RandomizerViewModel){
        ActionHelper.sendAction(CancelClickAction(), randomizerViewModel)
    }

    sealed class Filter {
        object None : Filter()
        object Price : Filter()
        object Category : Filter()
        object Distance : Filter()
        object Restriction : Filter()
    }

    fun renderButtonStyle(button: MaterialButton, selected: Boolean, context: Context) {
        if (selected) {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_selected))
        } else {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_not_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_not_selected))
        }
    }

    fun renderFilterStyle(button: MaterialButton, selected: Boolean, context: Context) {
        if (selected) {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_selected))
            button.setIconTintResource(R.color.filter_text_selected)
        }
        else {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_not_selected))
            button.setTextColor(ContextCompat.getColor(context, R.color.filter_text_not_selected))
            button.setIconTintResource(R.color.filter_text_not_selected)
        }
    }
}