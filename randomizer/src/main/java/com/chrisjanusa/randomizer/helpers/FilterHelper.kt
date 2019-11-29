package com.chrisjanusa.randomizer.helpers

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.actions.filter.CancelClickAction
import com.chrisjanusa.randomizer.filter_fragments.PriceFragment
import com.chrisjanusa.randomizer.models.RandomizerViewModel

object FilterHelper {
    const val defaultPriceTitle = "Price"

    fun getFilterFragment(filter: Filter) : Fragment? {
        return when(filter) {
            is Filter.Price -> PriceFragment()
            is Filter.Category -> null
            is Filter.Distance -> null
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

    sealed class Price(val text: String, val num: Int) {
        object One : Price("$",1)
        object Two : Price("$$",2)
        object Three : Price("$$$",3)
        object Four : Price("$$$$",4)
    }

    fun priceToDisplayString(list : List<Price>) : String {
        return if (list.isEmpty()) {
            defaultPriceTitle
        }
        else{
            val builder = StringBuilder()
            for (price in list) {
                builder.append(price.text)
                builder.append(", ")
            }
            builder.deleteCharAt(builder.lastIndex)
            builder.deleteCharAt(builder.lastIndex)
            builder.toString()
        }
    }

    fun priceFromDisplayString(curr : String) : List<Price> {
        return if (defaultPriceTitle == curr){
            ArrayList()
        }
        else{
            val selected = ArrayList<Price>()
            for (price in curr.split(", ")) {
                when (price) {
                    Price.One.text -> selected.add(Price.One)
                    Price.Two.text -> selected.add(Price.Two)
                    Price.Three.text -> selected.add(Price.Three)
                    Price.Four.text -> selected.add(Price.Four)
                }
            }
            selected
        }
    }
}