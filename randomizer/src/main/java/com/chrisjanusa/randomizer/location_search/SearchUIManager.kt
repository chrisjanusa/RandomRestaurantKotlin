package com.chrisjanusa.randomizer.location_search

import android.animation.LayoutTransition
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.location_search.actions.*
import com.seatgeek.placesautocomplete.DetailsCallback
import com.seatgeek.placesautocomplete.model.PlaceDetails
import kotlinx.android.synthetic.main.search_card.*

object SearchUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        initOpenListeners(randomizerViewModel, fragment)
        initCloseListeners(randomizerViewModel, fragment)
        initTransitionListeners(randomizerViewModel, fragment)
    }

    private fun initOpenListeners(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            user_input.setOnTouchListener { _, _ ->
                sendAction(
                    SearchGainFocusAction(
                        randomizerViewModel.state.value?.addressSearchString ?: ""
                    ), randomizerViewModel
                )
                true
            }

            search_icon.setOnClickListener {
                sendAction(
                    SearchGainFocusAction(randomizerViewModel.state.value?.addressSearchString ?: ""),
                    randomizerViewModel
                )
            }
        }
    }

    private fun initCloseListeners(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            back_icon.setOnClickListener {
                sendAction(SearchFinishedAction(user_input.text.toString()), randomizerViewModel)
            }

            user_input.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendAction(SearchFinishedAction(user_input.text.toString()), randomizerViewModel)
                    true
                } else {
                    false
                }
            }

            user_input.setOnPlaceSelectedListener { place ->
                sendAction(SearchFinishedAction(user_input.text.toString()), randomizerViewModel)
                user_input.getDetailsFor(place, object : DetailsCallback {
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

    private fun initTransitionListeners(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            search_bar.layoutTransition.addTransitionListener(object : LayoutTransition.TransitionListener {
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
                    if (view?.id == user_input.id && transitionType == LayoutTransition.CHANGE_APPEARING) {
                        sendAction(SearchOpenedAction(), randomizerViewModel)
                    }
                    if (view?.id == user_input.id && transitionType == LayoutTransition.CHANGE_DISAPPEARING) {
                        sendAction(SearchClosedAction(), randomizerViewModel)
                    }
                }
            })
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
    }
}