package com.chrisjanusa.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chrisjanusa.yelp.models.Restaurant

class HistoryFragment : Fragment() {
    private lateinit var historyList : List<Restaurant>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.history_frag, container, false)
    }


}