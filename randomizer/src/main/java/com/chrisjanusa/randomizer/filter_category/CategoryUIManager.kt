package com.chrisjanusa.randomizer.filter_category

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.defaultCategoryTitle
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.filters.*

object CategoryUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            categories.setOnClickListener { clickSelectionFilter(Filter.Category, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            val selected = state.categoryString != defaultCategoryTitle
            categories.text = toDisplayString(state.categoryString)
            context?.let { renderFilterStyle(categories, selected, it) }
        }
    }

    private fun toDisplayString(text: String): String =
        text.takeUnless { it.length > 20 } ?: text.substring(0..16) + "..."

    fun renderCategoryOptionCardStyle(
        card: MaterialCardView,
        text: TextView,
        icon: ImageView,
        selected: Boolean,
        context: Context
    ) {
        if (selected) {
            val background = ContextCompat.getColor(context, R.color.filter_background_selected)
            card.setCardBackgroundColor(background)
            card.strokeColor = background
            text.setTextColor(ContextCompat.getColor(context, R.color.filter_text_selected))
            val color = ContextCompat.getColor(context, R.color.filter_icon_selected)
            icon.setColorFilter(color)
        } else {
            card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_not_selected))
            card.strokeColor = ContextCompat.getColor(context, R.color.outline)
            text.setTextColor(ContextCompat.getColor(context, R.color.filter_text_not_selected))
            val color = ContextCompat.getColor(context, R.color.filter_icon_not_selected)
            icon.setColorFilter(color)
        }
    }
}