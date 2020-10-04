package com.chrisjanusa.randomizer.filter_base

import android.content.Context
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.filter_base.actions.CancelClickAction
import com.chrisjanusa.randomizer.filter_base.actions.ClickSelectionFilterAction
import com.chrisjanusa.randomizer.filter_cuisine.CuisineFragment
import com.chrisjanusa.randomizer.filter_diet.DietFragment
import com.chrisjanusa.randomizer.filter_distance.DistanceFragment
import com.chrisjanusa.randomizer.filter_price.PriceFragment
import com.chrisjanusa.randomizer.filter_rating.RatingFragment
import com.google.android.material.button.MaterialButton

object FilterHelper {
    fun onCancelFilterClick(randomizerViewModel: RandomizerViewModel) {
        sendAction(CancelClickAction(), randomizerViewModel)
    }

    fun clickSelectionFilter(filter: Filter, randomizerViewModel: RandomizerViewModel) {
        sendAction(ClickSelectionFilterAction(filter), randomizerViewModel)
    }

    fun renderFilterStyle(button: MaterialButton, selected: Boolean, context: Context) {
        button.setBackgroundColor(getColor(context, getBackgroundColor(selected)))
        button.setTextColor(getColor(context, getTextColor(selected)))
        button.setStrokeColorResource(getStrokeColor(selected))
    }


    fun renderFilterWithIconStyle(button: MaterialButton, selected: Boolean, context: Context) {
        renderFilterStyle(button, selected, context)
        button.setIconTintResource(getTextColor(selected))
    }

    fun getBackgroundColor(selected: Boolean) =
        if (selected) R.color.filter_background_selected else R.color.filter_background_not_selected

    fun getTextColor(selected: Boolean) =
        if (selected) R.color.filter_text_selected else R.color.filter_text_not_selected

    fun getStrokeColor(selected: Boolean) =
        if (selected) R.color.outline_selected else R.color.outline_not_selected

    fun getFragFromFilter(filter: Filter) : Fragment {
        return when(filter) {
            Filter.Price -> PriceFragment()
            Filter.Cuisine -> CuisineFragment()
            Filter.Distance -> DistanceFragment()
            Filter.Diet -> DietFragment()
            Filter.Rating -> RatingFragment()
        }
    }

}