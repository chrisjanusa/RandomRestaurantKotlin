package com.chrisjanusa.randomizer.helpers

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.actions.filter.CancelClickAction
import com.chrisjanusa.randomizer.filter_fragments.DistanceFragment
import com.chrisjanusa.randomizer.filter_fragments.PriceFragment
import com.chrisjanusa.randomizer.models.RandomizerViewModel

object FilterHelper {

    fun getFilterFragment(filter: Filter) : Fragment? {
        return when(filter) {
            is Filter.Price -> PriceFragment()
            is Filter.Category -> null
            is Filter.Distance -> DistanceFragment()
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
    }
}