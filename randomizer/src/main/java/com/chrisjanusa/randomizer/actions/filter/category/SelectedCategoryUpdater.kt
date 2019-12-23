package com.chrisjanusa.randomizer.actions.filter.category

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.helpers.CategoryHelper.Category
import com.chrisjanusa.randomizer.models.RandomizerState

class SelectedCategoryUpdater(private val categorySet: HashSet<Category>, private val display: String) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(categorySet = categorySet, categoryString = display)
    }
}