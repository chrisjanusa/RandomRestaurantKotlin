package com.chrisjanusa.randomizer.filter_cuisine

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.getBackgroundColor
import com.chrisjanusa.randomizer.filter_base.FilterHelper.getStrokeColor
import com.chrisjanusa.randomizer.filter_base.FilterHelper.getTextColor
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.isDefault
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.filters.*

object CuisineUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            cuisines.setOnClickListener { clickSelectionFilter(Filter.Cuisine, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            val selected = isDefault(state.cuisineString)
            cuisines.text = toDisplayString(state.cuisineString)
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