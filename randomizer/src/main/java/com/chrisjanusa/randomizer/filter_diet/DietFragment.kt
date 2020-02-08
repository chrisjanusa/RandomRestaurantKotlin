package com.chrisjanusa.randomizer.filter_diet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.base.CommunicationHelper.getViewModel
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderButtonStyle
import com.chrisjanusa.randomizer.filter_diet.DietHelper.Diet
import com.chrisjanusa.randomizer.filter_diet.actions.ApplyDietAction
import com.chrisjanusa.randomizer.filter_diet.actions.InitDietFilterAction
import com.chrisjanusa.randomizer.filter_diet.actions.ResetDietAction
import com.chrisjanusa.randomizer.filter_diet.actions.DietChangeAction
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.diet_filter_fragment.*
import kotlinx.android.synthetic.main.diet_filter_fragment.shade

class DietFragment : Fragment() {
    private lateinit var randomizerViewModel: RandomizerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomizerViewModel = activity?.let { getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.diet_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        confirm.setOnClickListener { sendAction(ApplyDietAction(), randomizerViewModel) }
        cancel.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        reset.setOnClickListener { sendAction(ResetDietAction(), randomizerViewModel) }

        halal.setOnClickListener { dietClick(Diet.Halal) }
        vegan.setOnClickListener { dietClick(Diet.Vegan) }
        vegetarian.setOnClickListener { dietClick(Diet.Vegetarian) }
        kosher.setOnClickListener { dietClick(Diet.Kosher) }

        randomizerViewModel.state.observe(this, Observer<RandomizerState>(render))
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitDietFilterAction(), randomizerViewModel)
    }

    private fun dietClick(diet: Diet) {
        sendAction(DietChangeAction(diet), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        context?.let {
            renderButtonStyle(halal, Diet.Halal == newState.dietTempSelected, it)
            renderButtonStyle(vegan, Diet.Vegan == newState.dietTempSelected, it)
            renderButtonStyle(vegetarian, Diet.Vegetarian == newState.dietTempSelected, it)
            renderButtonStyle(kosher, Diet.Kosher == newState.dietTempSelected, it)
        }
    }
}
