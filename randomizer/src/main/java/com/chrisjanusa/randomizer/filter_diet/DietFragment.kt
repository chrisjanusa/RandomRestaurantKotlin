package com.chrisjanusa.randomizer.filter_diet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chrisjanusa.base.CommunicationHelper.getViewModel
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.base.models.enums.Diet
import com.chrisjanusa.randomizer.databinding.DietFilterFragmentBinding
import com.chrisjanusa.randomizer.filter_diet.actions.ApplyDietAction
import com.chrisjanusa.randomizer.filter_diet.actions.DietChangeAction
import com.chrisjanusa.randomizer.filter_diet.actions.InitDietFilterAction
import com.chrisjanusa.randomizer.filter_diet.actions.ResetDietAction

class DietFragment : Fragment() {
    private var _binding: DietFilterFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DietFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
            confirmationButtons.confirm.setOnClickListener { sendAction(ApplyDietAction(), randomizerViewModel) }
            xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
            confirmationButtons.reset.setOnClickListener { sendAction(ResetDietAction(), randomizerViewModel) }


            halal.setOnClickListener { dietClick(Diet.Halal) }
            vegan.setOnClickListener { dietClick(Diet.Vegan) }
            vegetarian.setOnClickListener { dietClick(Diet.Vegetarian) }
            kosher.setOnClickListener { dietClick(Diet.Kosher) }
        }
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
            binding.run {
                renderFilterStyle(halal, Diet.Halal == newState.dietTempSelected, it)
                renderFilterStyle(vegan, Diet.Vegan == newState.dietTempSelected, it)
                renderFilterStyle(vegetarian, Diet.Vegetarian == newState.dietTempSelected, it)
                renderFilterStyle(kosher, Diet.Kosher == newState.dietTempSelected, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
