package com.chrisjanusa.randomizer.filter_distance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.filter_distance.actions.InitDistanceFilterAction
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistance
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.distanceToPercent
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.maxDistance
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.minDistance
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.percentToDistance
import com.chrisjanusa.randomizer.filter_base.FilterHelper
import com.chrisjanusa.randomizer.filter_distance.actions.ApplyDistanceAction
import com.chrisjanusa.randomizer.filter_distance.actions.DistanceChangeAction
import com.chrisjanusa.randomizer.filter_distance.actions.ResetDistanceAction
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.ramotion.fluidslider.FluidSlider
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.distance_filter_fragment.*

class DistanceFragment : Fragment() {
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
        return inflater.inflate(R.layout.distance_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm.setOnClickListener { sendAction(ApplyDistanceAction(), randomizerViewModel) }
        cancel.setOnClickListener { FilterHelper.onCancelFilterClick(randomizerViewModel) }
        reset.setOnClickListener { sendAction(ResetDistanceAction(), randomizerViewModel) }

        val slider = distance as FluidSlider
        slider.positionListener = { pos -> slider.bubbleText = "%.1f".format(percentToDistance(pos))}
        slider.position = distanceToPercent(defaultDistance)
        slider.startText ="$minDistance miles"
        slider.endText = "$maxDistance miles"
        slider.endTrackingListener = { distanceChange(percentToDistance(slider.position))}

        randomizerViewModel.state.observe(this, Observer<RandomizerState>(render))
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