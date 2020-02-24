package com.chrisjanusa.randomizer.filter_cuisine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.base.CommunicationHelper
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.Cuisine
import com.chrisjanusa.randomizer.filter_cuisine.CuisineUIManager.renderCuisineCard
import com.chrisjanusa.randomizer.filter_cuisine.actions.ApplyCuisineAction
import com.chrisjanusa.randomizer.filter_cuisine.actions.CuisineChangeAction
import com.chrisjanusa.randomizer.filter_cuisine.actions.InitCuisineFilterAction
import com.chrisjanusa.randomizer.filter_cuisine.actions.ResetCuisineAction
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.cuisines.*
import kotlinx.android.synthetic.main.price_filter_fragment.*

class CuisineFragment : Fragment() {
    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { CommunicationHelper.getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.cuisine_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        confirm.setOnClickListener { sendAction(ApplyCuisineAction(), randomizerViewModel) }
        xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        reset.setOnClickListener { sendAction(ResetCuisineAction(), randomizerViewModel) }

        american.setOnClickListener { cuisineClick(Cuisine.American) }
        asian.setOnClickListener { cuisineClick(Cuisine.Asian) }
        bbq.setOnClickListener { cuisineClick(Cuisine.Bbq) }
        deli.setOnClickListener { cuisineClick(Cuisine.Deli) }
        desserts.setOnClickListener { cuisineClick(Cuisine.Dessert) }
        italian.setOnClickListener { cuisineClick(Cuisine.Italian) }
        indian.setOnClickListener { cuisineClick(Cuisine.Indian) }
        mexican.setOnClickListener { cuisineClick(Cuisine.Mexican) }
        pizza.setOnClickListener { cuisineClick(Cuisine.Pizza) }
        seafood.setOnClickListener { cuisineClick(Cuisine.Seafood) }
        steak.setOnClickListener { cuisineClick(Cuisine.Steak) }
        sushi.setOnClickListener { cuisineClick(Cuisine.Sushi) }
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitCuisineFilterAction(), randomizerViewModel)
    }

    private fun cuisineClick(cuisine: Cuisine) {
        sendAction(CuisineChangeAction(cuisine), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        context?.let {
            newState.cuisineTempSet.run {
                renderCuisineCard(american, american_description, american_icon, contains(Cuisine.American), it)
                renderCuisineCard(asian, asian_description, asian_icon, contains(Cuisine.Asian), it)
                renderCuisineCard(bbq, bbq_description, bbq_icon, contains(Cuisine.Bbq), it)
                renderCuisineCard(deli, deli_description, deli_icon, contains(Cuisine.Deli), it)
                renderCuisineCard(desserts, desserts_description, desserts_icon, contains(Cuisine.Dessert), it)
                renderCuisineCard(italian, italian_description, italian_icon, contains(Cuisine.Italian), it)
                renderCuisineCard(indian, indian_description, indian_icon, contains(Cuisine.Indian), it)
                renderCuisineCard(mexican, mexican_description, mexican_icon, contains(Cuisine.Mexican), it)
                renderCuisineCard(pizza, pizza_description, pizza_icon, contains(Cuisine.Pizza), it)
                renderCuisineCard(seafood, seafood_description, seafood_icon, contains(Cuisine.Seafood), it)
                renderCuisineCard(steak, steak_description, steak_icon, contains(Cuisine.Steak), it)
                renderCuisineCard(sushi, sushi_description, sushi_icon, contains(Cuisine.Sushi), it)
            }
        }
    }
}