package com.chrisjanusa.randomizer.filter_rating

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
import com.chrisjanusa.randomizer.databinding.RatingFilterFragmentBinding
import com.chrisjanusa.randomizer.filter_base.FilterHelper.onCancelFilterClick
import com.chrisjanusa.randomizer.filter_rating.actions.ApplyRatingAction
import com.chrisjanusa.randomizer.filter_rating.actions.InitRatingFilterAction
import com.chrisjanusa.randomizer.filter_rating.actions.RatingChangeAction
import com.chrisjanusa.randomizer.filter_rating.actions.ResetRatingAction

class RatingFragment : Fragment() {
    private var _binding: RatingFilterFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

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
        _binding = RatingFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Synthetics
        binding.run {
            shade.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
            confirmationButtons.confirm.setOnClickListener { sendAction(ApplyRatingAction(), randomizerViewModel) }
            xout.setOnClickListener { onCancelFilterClick(randomizerViewModel) }
            confirmationButtons.reset.setOnClickListener { sendAction(ResetRatingAction(), randomizerViewModel) }

            ratingbar.setOnRatingBarChangeListener { _, rating, _ ->  ratingChange(rating)}
        }
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitRatingFilterAction(), randomizerViewModel)
    }

    private fun ratingChange(rating: Float) {
        sendAction(RatingChangeAction(rating), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        binding.ratingbar.rating = newState.tempMinRating
    }
}