package com.chrisjanusa.randomizer.filter_price

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
import com.chrisjanusa.base.models.enums.Price
import com.chrisjanusa.randomizer.databinding.PriceFilterFragmentBinding
import com.chrisjanusa.randomizer.filter_base.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.randomizer.filter_price.actions.ApplyPriceAction
import com.chrisjanusa.randomizer.filter_price.actions.InitPriceFilterAction
import com.chrisjanusa.randomizer.filter_price.actions.PriceChangeAction
import com.chrisjanusa.randomizer.filter_price.actions.ResetPriceAction

class PriceFragment : Fragment() {
    private var _binding: PriceFilterFragmentBinding? = null

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
        _binding = PriceFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
            confirmationButtons.confirm.setOnClickListener { sendAction(ApplyPriceAction(), randomizerViewModel) }
            xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
            confirmationButtons.reset.setOnClickListener { sendAction(ResetPriceAction(), randomizerViewModel) }
            price1.setOnClickListener { priceClick(Price.One) }
            price2.setOnClickListener { priceClick(Price.Two) }
            price3.setOnClickListener { priceClick(Price.Three) }
            price4.setOnClickListener { priceClick(Price.Four) }
        }
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitPriceFilterAction(), randomizerViewModel)
    }

    private fun priceClick(price: Price) {
        sendAction(PriceChangeAction(price), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        context?.let {
            binding.run {
            renderFilterStyle(price1, newState.priceTempSet.contains(Price.One), it)
            renderFilterStyle(price2, newState.priceTempSet.contains(Price.Two), it)
            renderFilterStyle(price3, newState.priceTempSet.contains(Price.Three), it)
            renderFilterStyle(price4, newState.priceTempSet.contains(Price.Four), it)
            }
        }
    }
}