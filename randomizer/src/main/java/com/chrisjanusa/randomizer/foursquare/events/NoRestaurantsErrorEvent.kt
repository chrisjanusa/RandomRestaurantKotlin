package com.chrisjanusa.randomizer.foursquare.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager.LayoutParams
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.databinding.ErrorDialogBinding

class NoRestaurantsErrorEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let {
            val dialogMainBinding = ErrorDialogBinding.inflate(fragment.layoutInflater)
            val dialog = Dialog(it)
            dialog.setContentView(dialogMainBinding.root)
            dialogMainBinding.ok.setOnClickListener { dialog.dismiss() }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            dialog.show()
        }
    }
}