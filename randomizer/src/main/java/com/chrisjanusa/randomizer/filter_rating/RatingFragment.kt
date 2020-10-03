package com.chrisjanusa.randomizer.filter_rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chrisjanusa.base.CommunicationHelper.getViewModel
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.defaultDistance
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.filter_base.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.filter_rating.actions.ApplyRatingAction
import com.chrisjanusa.randomizer.filter_rating.actions.InitRatingFilterAction
import com.chrisjanusa.randomizer.filter_rating.actions.RatingChangeAction
import com.chrisjanusa.randomizer.filter_rating.actions.ResetRatingAction
import com.ramotion.fluidslider.FluidSlider
import kotlinx.android.synthetic.main.confirmation_buttons.*
import kotlinx.android.synthetic.main.distance_filter_fragment.*
import kotlinx.android.synthetic.main.distance_filter_fragment.distance
import kotlinx.android.synthetic.main.distance_filter_fragment.shade
import kotlinx.android.synthetic.main.distance_filter_fragment.xout
import kotlinx.android.synthetic.main.filters.*
import kotlinx.android.synthetic.main.rating_filter_fragment.*

class RatingFragment : Fragment() {
    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.rating_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        confirm.setOnClickListener { sendAction(ApplyRatingAction(), randomizerViewModel) }
        xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
        reset.setOnClickListener { sendAction(ResetRatingAction(), randomizerViewModel) }

        ratingbar.setOnRatingBarChangeListener { _, rating, _ ->  ratingChange(rating)}
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitRatingFilterAction(), randomizerViewModel)
    }

    private fun ratingChange(rating: Float) {
        sendAction(RatingChangeAction(rating), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        ratingbar.rating = newState.tempMinRating
    }
}