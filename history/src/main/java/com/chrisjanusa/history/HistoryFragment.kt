package com.chrisjanusa.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chrisjanusa.base.CommunicationHelper.getViewModel
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.base.preferences.PreferenceHelper
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { getViewModel(it) } ?: throw Exception("Invalid Activity")
    }

    private val featureUIManagers = listOf(HistoryListUIManager)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val render = fun(newState: RandomizerState) {
            for (uiManager in featureUIManagers) {
                uiManager.render(newState, this)
            }
        }
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.history_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (uiManager in featureUIManagers) {
            uiManager.init(randomizerViewModel, this)
        }

        val frag = this
        lifecycleScope.launch {
            for (event in randomizerViewModel.eventChannel) {
                event.handleEvent(frag)
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