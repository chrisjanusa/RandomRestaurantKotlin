package com.chrisjanusa.randomizer.filter_price

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
import com.chrisjanusa.randomizer.filter_price.PriceHelper.Price
import com.chrisjanusa.randomizer.filter_price.actions.ApplyPriceAction
import com.chrisjanusa.randomizer.filter_price.actions.InitPriceFilterAction
import com.chrisjanusa.randomizer.filter_price.actions.PriceChangeAction
import com.chrisjanusa.randomizer.filter_price.actions.ResetPriceAction
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.price_filter_fragment.*

class PriceFragment : Fragment() {
    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.price_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        confirm.setOnClickListener { sendAction(ApplyPriceAction(), randomizerViewModel) }
        xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        reset.setOnClickListener { sendAction(ResetPriceAction(), randomizerViewModel) }
        price1.setOnClickListener { priceClick(Price.One) }
        price2.setOnClickListener { priceClick(Price.Two) }
        price3.setOnClickListener { priceClick(Price.Three) }
        price4.setOnClickListener { priceClick(Price.Four) }
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
            renderFilterStyle(price1, newState.priceTempSet.contains(Price.One), it)
            renderFilterStyle(price2, newState.priceTempSet.contains(Price.Two), it)
            renderFilterStyle(price3, newState.priceTempSet.contains(Price.Three), it)
            renderFilterStyle(price4, newState.priceTempSet.contains(Price.Four), it)
        }
    }
}