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
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.randomizer.filter_diet.DietHelper.Diet
import com.chrisjanusa.randomizer.filter_diet.actions.ApplyDietAction
import com.chrisjanusa.randomizer.filter_diet.actions.DietChangeAction
import com.chrisjanusa.randomizer.filter_diet.actions.InitDietFilterAction
import com.chrisjanusa.randomizer.filter_diet.actions.ResetDietAction
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.diet_filter_fragment.*

class DietFragment : Fragment() {
    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.diet_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        confirm.setOnClickListener { sendAction(ApplyDietAction(), randomizerViewModel) }
        xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        reset.setOnClickListener { sendAction(ResetDietAction(), randomizerViewModel) }

        halal.setOnClickListener { dietClick(Diet.Halal) }
        vegan.setOnClickListener { dietClick(Diet.Vegan) }
        vegetarian.setOnClickListener { dietClick(Diet.Vegetarian) }
        kosher.setOnClickListener { dietClick(Diet.Kosher) }
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
            renderFilterStyle(halal, Diet.Halal == newState.dietTempSelected, it)
            renderFilterStyle(vegan, Diet.Vegan == newState.dietTempSelected, it)
            renderFilterStyle(vegetarian, Diet.Vegetarian == newState.dietTempSelected, it)
            renderFilterStyle(kosher, Diet.Kosher == newState.dietTempSelected, it)
        }
    }
}
