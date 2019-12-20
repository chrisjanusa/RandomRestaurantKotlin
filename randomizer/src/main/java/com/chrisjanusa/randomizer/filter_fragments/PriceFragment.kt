package com.chrisjanusa.randomizer.filter_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.actions.filter.price.ApplyPriceAction
import com.chrisjanusa.randomizer.actions.filter.price.PriceChangeAction
import com.chrisjanusa.randomizer.actions.init.InitPriceFilterAction
import com.chrisjanusa.randomizer.helpers.ActionHelper.sendAction
import com.chrisjanusa.randomizer.helpers.FilterHelper
import com.chrisjanusa.randomizer.helpers.FilterHelper.renderButtonStyle
import com.chrisjanusa.randomizer.models.RandomizerState
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.price_filter_fragment.*

class PriceFragment : Fragment() {
    private lateinit var randomizerViewModel : RandomizerViewModel

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
        return inflater.inflate(R.layout.price_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm.setOnClickListener { sendAction(ApplyPriceAction(), randomizerViewModel) }
        cancel.setOnClickListener { FilterHelper.onCancelFilterClick(randomizerViewModel) }
        price1.setOnClickListener {  priceClick(1)}
        price2.setOnClickListener {  priceClick(2)}
        price3.setOnClickListener {  priceClick(3)}
        price4.setOnClickListener {  priceClick(4)}

        randomizerViewModel.state.observe(this, Observer<RandomizerState>(render))
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitPriceFilterAction(), randomizerViewModel)
    }

    private fun priceClick(price: Int) {
        sendAction(PriceChangeAction(price), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        context?.let {
            renderButtonStyle(price1, newState.price1TempSelected, it)
            renderButtonStyle(price2, newState.price2TempSelected, it)
            renderButtonStyle(price3, newState.price3TempSelected, it)
            renderButtonStyle(price4, newState.price4TempSelected, it)
        }
    }
}