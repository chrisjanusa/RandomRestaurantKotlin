package com.chrisjanusa.randomizer.filter_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.actions.filter.restriction.ApplyRestrictionAction
import com.chrisjanusa.randomizer.actions.filter.restriction.RestrictionChangeAction
import com.chrisjanusa.randomizer.actions.init.InitRestrictionFilterAction
import com.chrisjanusa.randomizer.helpers.ActionHelper
import com.chrisjanusa.randomizer.helpers.FilterHelper.renderButtonStyle
import com.chrisjanusa.randomizer.helpers.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.helpers.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.models.RandomizerState
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.restrictions_filter_fragment.*

class RestrictionFragment : Fragment() {
    private lateinit var randomizerViewModel: RandomizerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomizerViewModel = activity?.run {
            ViewModelProviders.of(this)[RandomizerViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.restrictions_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm.setOnClickListener { ActionHelper.sendAction(ApplyRestrictionAction(), randomizerViewModel) }
        cancel.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        halal.setOnClickListener { restrictionClick(Restriction.Halal) }
        vegan.setOnClickListener { restrictionClick(Restriction.Vegan) }
        vegetarian.setOnClickListener { restrictionClick(Restriction.Vegetarian) }
        kosher.setOnClickListener { restrictionClick(Restriction.Kosher) }

        randomizerViewModel.state.observe(this, Observer<RandomizerState>(render))
    }

    override fun onResume() {
        super.onResume()
        ActionHelper.sendAction(InitRestrictionFilterAction(), randomizerViewModel)
    }

    private fun restrictionClick(restriction: Restriction) {
        ActionHelper.sendAction(RestrictionChangeAction(restriction), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        context?.let {
            renderButtonStyle(halal, Restriction.Halal == newState.restrictionTempSelected, it)
            renderButtonStyle(vegan, Restriction.Vegan == newState.restrictionTempSelected, it)
            renderButtonStyle(vegetarian, Restriction.Vegetarian == newState.restrictionTempSelected, it)
            renderButtonStyle(kosher, Restriction.Kosher == newState.restrictionTempSelected, it)
        }
    }
}
