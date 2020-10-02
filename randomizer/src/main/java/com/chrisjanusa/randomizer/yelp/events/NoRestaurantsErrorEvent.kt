package com.chrisjanusa.randomizer.yelp.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager.LayoutParams
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent
import com.google.android.material.button.MaterialButton

class NoRestaurantsErrorEvent : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        fragment.context?.let {
            val dialog = Dialog(it)
            dialog.setContentView(R.layout.error_dialog)
            (dialog.findViewById(R.id.ok) as MaterialButton).setOnClickListener { dialog.dismiss() }
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            dialog.show()
        }
    }
}