package com.chrisjanusa.base.interfaces

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.base.CommunicationHelper
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.preferences.PreferenceHelper
import kotlinx.coroutines.launch
import java.util.*

open class BaseRestaurantFragment : Fragment() {
    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { CommunicationHelper.getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    open fun getFeatureUIManagers() : List<FeatureUIManager> = LinkedList()

    open fun getFragmentDetails() : BaseFragmentDetails = BaseFragmentDetails(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val render = fun(newState: RandomizerState) {
            for (uiManager in getFeatureUIManagers()) {
                uiManager.render(newState, getFragmentDetails())
            }
        }
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (uiManager in getFeatureUIManagers()) {
            uiManager.init(randomizerViewModel, getFragmentDetails())
        }

        lifecycleScope.launch {
            for (event in randomizerViewModel.eventChannel) {
                event.handleEvent(this@BaseRestaurantFragment)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        randomizerViewModel.state.value?.let {
            PreferenceHelper.saveState(it, activity?.getPreferences(Context.MODE_PRIVATE))
        }
    }
}