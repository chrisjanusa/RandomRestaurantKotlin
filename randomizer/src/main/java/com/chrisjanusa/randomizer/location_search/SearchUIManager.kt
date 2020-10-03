package com.chrisjanusa.randomizer.location_search

import android.animation.LayoutTransition
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.location_search.actions.*
import com.seatgeek.placesautocomplete.DetailsCallback
import com.seatgeek.placesautocomplete.model.PlaceDetails
import kotlinx.android.synthetic.main.search_card.*
import kotlinx.android.synthetic.main.top_overlay.*

object SearchUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: BaseRestaurantFragment) {
        initOpenListeners(randomizerViewModel, fragment)
        initCloseListeners(randomizerViewModel, fragment)
        initTransitionListeners(randomizerViewModel, fragment)
    }

    private fun initOpenListeners(randomizerViewModel: RandomizerViewModel, fragment: Fragment) {
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

    private fun initCloseListeners(randomizerViewModel: RandomizerViewModel, fragment: Fragment) {
        fragment.run {
            back_icon.setOnClickListener {
                sendAction(SearchFinishedAction(user_input.text.toString()), randomizerViewModel)
            }

            search_shade.setOnClickListener {
                sendAction(SearchFinishedAction(user_input.text.toString()), randomizerViewModel)
            }

            // Close search bar if user hits enter
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

    private fun initTransitionListeners(randomizerViewModel: RandomizerViewModel, fragment: Fragment) {
        fragment.run {
            // Listen for when the search bar has finished opening or closing to send off that event
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

    override fun render(state: RandomizerState, fragment: Fragment) {
    }
}