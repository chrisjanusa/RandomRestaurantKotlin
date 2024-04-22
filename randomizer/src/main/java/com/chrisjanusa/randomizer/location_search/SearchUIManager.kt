package com.chrisjanusa.randomizer.location_search

import android.animation.LayoutTransition
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.databinding.SearchCardBinding
import com.chrisjanusa.randomizer.databinding.ShadeOverlayBinding
import com.chrisjanusa.randomizer.location_search.actions.*
import com.seatgeek.placesautocomplete.DetailsCallback
import com.seatgeek.placesautocomplete.model.PlaceDetails

object SearchUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val searchBinding = baseFragmentDetails.binding.locationContainer.searchCard
                initOpenListeners(randomizerViewModel, searchBinding)
                initCloseListeners(randomizerViewModel, searchBinding, baseFragmentDetails.binding.locationContainer.searchShade, context)
                initTransitionListeners(randomizerViewModel, searchBinding)
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {

    }

    private fun initOpenListeners(randomizerViewModel: RandomizerViewModel, searchBinding: SearchCardBinding) {
        searchBinding.userInput.setOnTouchListener { _, _ ->
            sendAction(
                SearchGainFocusAction(
                    randomizerViewModel.state.value?.addressSearchString ?: ""
                ), randomizerViewModel
            )
            true
        }

        searchBinding.searchIcon.setOnClickListener {
            sendAction(
                SearchGainFocusAction(randomizerViewModel.state.value?.addressSearchString ?: ""),
                randomizerViewModel
            )
        }
    }

    private fun initCloseListeners(randomizerViewModel: RandomizerViewModel, searchBinding: SearchCardBinding, searchShade: ShadeOverlayBinding, context: Context?) {
        searchBinding.run {
            backIcon.setOnClickListener {
                sendAction(SearchFinishedAction(userInput.text.toString()), randomizerViewModel)
            }

            searchShade.root.setOnClickListener {
                sendAction(SearchFinishedAction(userInput.text.toString()), randomizerViewModel)
            }

            // Close search bar if user hits enter
            userInput.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendAction(SearchFinishedAction(userInput.text.toString()), randomizerViewModel)
                    true
                } else {
                    false
                }
            }

            userInput.setOnPlaceSelectedListener { place ->
                sendAction(SearchFinishedAction(userInput.text.toString()), randomizerViewModel)
                userInput.getDetailsFor(place, object : DetailsCallback {
                    override fun onSuccess(details: PlaceDetails) {
                        context?.let {
                            sendAction(LocationChosenAction(details, it), randomizerViewModel)
                        }
                    }

                    override fun onFailure(failure: Throwable) {

                    }
                })

            }
        }
    }

    private fun initTransitionListeners(randomizerViewModel: RandomizerViewModel, searchBinding: SearchCardBinding) {
        // Listen for when the search bar has finished opening or closing to send off that event
        searchBinding.searchBar.layoutTransition.addTransitionListener(object : LayoutTransition.TransitionListener {
            override fun startTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {

            }

            override fun endTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {
                if (view?.id == searchBinding.userInput.id && transitionType == LayoutTransition.CHANGE_APPEARING) {
                    sendAction(SearchOpenedAction(), randomizerViewModel)
                }
                if (view?.id == searchBinding.userInput.id && transitionType == LayoutTransition.CHANGE_DISAPPEARING) {
                    sendAction(SearchClosedAction(), randomizerViewModel)
                }
            }
        })
    }
}