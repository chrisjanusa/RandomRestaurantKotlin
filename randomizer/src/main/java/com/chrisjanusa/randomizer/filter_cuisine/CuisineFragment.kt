package com.chrisjanusa.randomizer.filter_cuisine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chrisjanusa.base.CommunicationHelper
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.enums.Cuisine
import com.chrisjanusa.randomizer.databinding.CuisineFilterFragmentBinding
import com.chrisjanusa.randomizer.filter_base.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.filter_cuisine.CuisineUIManager.renderCuisineCard
import com.chrisjanusa.randomizer.filter_cuisine.actions.ApplyCuisineAction
import com.chrisjanusa.randomizer.filter_cuisine.actions.CuisineChangeAction
import com.chrisjanusa.randomizer.filter_cuisine.actions.InitCuisineFilterAction
import com.chrisjanusa.randomizer.filter_cuisine.actions.ResetCuisineAction


class CuisineFragment : Fragment() {
    private var _binding: CuisineFilterFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { CommunicationHelper.getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = CuisineFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
            xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        }

        binding.confirmationButtons.run {
            confirm.setOnClickListener { sendAction(ApplyCuisineAction(), randomizerViewModel) }
            reset.setOnClickListener { sendAction(ResetCuisineAction(), randomizerViewModel) }
        }

        binding.grid.run {
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
            binding.grid.run {
                renderCuisineCard(american, americanDescription, americanIcon, newState.cuisineTempSet.contains(Cuisine.American), it)
                renderCuisineCard(asian, asianDescription, asianIcon, newState.cuisineTempSet.contains(Cuisine.Asian), it)
                renderCuisineCard(bbq, bbqDescription, bbqIcon, newState.cuisineTempSet.contains(Cuisine.Bbq), it)
                renderCuisineCard(deli, deliDescription, deliIcon, newState.cuisineTempSet.contains(Cuisine.Deli), it)
                renderCuisineCard(desserts, dessertsDescription, dessertsIcon, newState.cuisineTempSet.contains(Cuisine.Dessert), it)
                renderCuisineCard(italian, italianDescription, italianIcon, newState.cuisineTempSet.contains(Cuisine.Italian), it)
                renderCuisineCard(indian, indianDescription, indianIcon, newState.cuisineTempSet.contains(Cuisine.Indian), it)
                renderCuisineCard(mexican, mexicanDescription, mexicanIcon, newState.cuisineTempSet.contains(Cuisine.Mexican), it)
                renderCuisineCard(pizza, pizzaDescription, pizzaIcon, newState.cuisineTempSet.contains(Cuisine.Pizza), it)
                renderCuisineCard(seafood, seafoodDescription, seafoodIcon, newState.cuisineTempSet.contains(Cuisine.Seafood), it)
                renderCuisineCard(steak, steakDescription, steakIcon, newState.cuisineTempSet.contains(Cuisine.Steak), it)
                renderCuisineCard(sushi, sushiDescription, sushiIcon, newState.cuisineTempSet.contains(Cuisine.Sushi), it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}