package com.chrisjanusa.randomizer.filter_boolean

import android.content.Context
import androidx.core.content.ContextCompat.getColor
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_boolean.actions.FavoriteClickedAction
import com.chrisjanusa.randomizer.filter_boolean.actions.OpenNowClickedAction
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.filters.*

object BooleanFilterUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            open_now.setOnClickListener { sendAction(OpenNowClickedAction(), randomizerViewModel) }
            favorites_only.setOnClickListener { sendAction(FavoriteClickedAction(), randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            context?.let {
                renderBooleanButton(state.openNowSelected, open_now, it)
                renderBooleanButton(state.favoriteOnlySelected, favorites_only, it)
            }
        }
    }

    private fun renderBooleanButton(selected: Boolean, button: MaterialButton, context: Context) {
        if (selected) {
            button.setBackgroundColor(getColor(context, R.color.filter_background_selected))
            button.setTextColor(getColor(context, R.color.filter_text_selected))
        } else {
            button.setBackgroundColor(getColor(context, R.color.filter_background_not_selected))
            button.setTextColor(getColor(context, R.color.filter_text_not_selected))
        }
    }
}