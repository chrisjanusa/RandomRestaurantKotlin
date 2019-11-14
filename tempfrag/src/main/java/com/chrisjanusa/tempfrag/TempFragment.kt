package com.chrisjanusa.tempfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.temp_frag.*

class TempFragment : Fragment() {

    private var display = "Default"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.temp_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        message.text = display
    }

    fun setText(text : String){
        if(message != null) {
            message.text = text
            display = text
        }
        else{
            display = text
        }
    }
}