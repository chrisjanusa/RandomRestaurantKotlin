package com.chrisjanusa.randomizer.filter_cuisine

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.defaultCuisineTitle
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
            val selected = state.cuisineString != defaultCuisineTitle
            cuisines.text = toDisplayString(state.cuisineString)
            context?.let { renderFilterStyle(cuisines, selected, it) }
        }
    }

    private fun toDisplayString(text: String): String =
        text.takeUnless { it.length > 20 } ?: text.substring(0..16) + "..."

    fun renderCuisineCard(card: MaterialCardView, text: TextView, icon: ImageView, chosen: Boolean, context: Context) {
        val backgroundColor = if (chosen) R.color.filter_background_selected else R.color.filter_background_not_selected
        val strokeColor = if (chosen) R.color.outline_selected else R.color.outline_not_selected
        val textColor = if (chosen) R.color.filter_text_selected else R.color.filter_text_not_selected

        card.setCardBackgroundColor(getColor(context, backgroundColor))
        card.strokeColor = getColor(context, strokeColor)
        val color = getColor(context, textColor)
        text.setTextColor(color)
        icon.setColorFilter(color)
    }
}