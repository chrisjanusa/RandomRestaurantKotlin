package com.chrisjanusa.randomizer.location_search.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.databinding.ErrorDialogBinding

class LocationSelectedErrorEvent(private val name: String) : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let { context ->
            val dialogMainBinding = ErrorDialogBinding.inflate(fragment.layoutInflater)
            val dialog = Dialog(context)
            dialog.setContentView(dialogMainBinding.root)

            dialogMainBinding.ok.setOnClickListener { dialog.cancel() }
            dialogMainBinding.message.text = "Unable to find location ($name). Please select a different location or use current location."
            dialogMainBinding.title.text = context.getString(R.string.InvalidLocationTittle)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.show()
        }
    }

}