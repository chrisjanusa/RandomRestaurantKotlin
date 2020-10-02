package com.chrisjanusa.randomizer.filter_cuisine

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.defaultCuisineTitle
import com.chrisjanusa.base.models.delimiter
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.getBackgroundColor
import com.chrisjanusa.randomizer.filter_base.FilterHelper.getStrokeColor
import com.chrisjanusa.randomizer.filter_base.FilterHelper.getTextColor
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.filters.*

object CuisineUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: BaseRestaurantFragment) {
        fragment.run {
            cuisines.setOnClickListener { clickSelectionFilter(Filter.Cuisine, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {
        fragment.run {
            val selected = state.cuisineSet.isNotEmpty()
            val cuisineSet = state.cuisineSet
            cuisines.text = if (cuisineSet.isEmpty()) {
                toDisplayString(defaultCuisineTitle)
            } else {
                val out = StringBuilder()
                for (cuisine in cuisineSet.iterator()) {
                    out.append(cuisine.identifier)
                    out.append(delimiter)
                }
                toDisplayString(out.dropLast(2).toString())
            }
            context?.let { renderFilterWithIconStyle(cuisines, selected, it) }
        }
    }

    private fun toDisplayString(text: String): String =
        text.takeUnless { it.length > 20 } ?: text.substring(0..16) + "..."

    fun renderCuisineCard(card: MaterialCardView, text: TextView, icon: ImageView, selected: Boolean, context: Context) {
        card.setCardBackgroundColor(getColor(context, getBackgroundColor(selected)))
        card.strokeColor = getColor(context, getStrokeColor(selected))
        val color = getColor(context, getTextColor(selected))
        text.setTextColor(color)
        icon.setColorFilter(color)
    }
}