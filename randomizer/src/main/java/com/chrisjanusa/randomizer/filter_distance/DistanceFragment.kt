package com.chrisjanusa.randomizer.filter_distance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.CommunicationHelper.getViewModel
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.defaultDistance
import com.chrisjanusa.randomizer.filter_base.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.filter_distance.actions.ApplyDistanceAction
import com.chrisjanusa.randomizer.filter_distance.actions.DistanceChangeAction
import com.chrisjanusa.randomizer.filter_distance.actions.InitDistanceFilterAction
import com.chrisjanusa.randomizer.filter_distance.actions.ResetDistanceAction
import com.ramotion.fluidslider.FluidSlider
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.distance_filter_fragment.*

class DistanceFragment : Fragment() {
    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.distance_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        confirm.setOnClickListener { sendAction(ApplyDistanceAction(), randomizerViewModel) }
        xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        reset.setOnClickListener { sendAction(ResetDistanceAction(), randomizerViewModel) }

        val slider = distance as FluidSlider
        slider.positionListener = { pos -> slider.bubbleText = "%.1f".format(percentToDistance(pos)) }
        slider.position = distanceToPercent(defaultDistance)
        slider.startText = "$minDistance miles"
        slider.endText = "$maxDistance miles"
        slider.endTrackingListener = { distanceChange(percentToDistance(slider.position)) }
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitDistanceFilterAction(), randomizerViewModel)
    }

    private fun distanceChange(dist: Float) {
        sendAction(DistanceChangeAction(dist), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        distance.position = distanceToPercent(newState.tempMaxMiles)
    }
}