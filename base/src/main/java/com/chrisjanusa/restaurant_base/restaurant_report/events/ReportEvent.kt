package com.chrisjanusa.restaurant_base.restaurant_report.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.R
import com.chrisjanusa.base.databinding.ReportRestaurantDialogBinding
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.restaurant.Restaurant
import com.chrisjanusa.restaurant_base.restaurant_report.actions.ReportConfirmAction

class ReportEvent(private val restaurant: Restaurant) : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let { context ->
            val dialogMainBinding = ReportRestaurantDialogBinding.inflate(fragment.layoutInflater)
            val dialog = Dialog(context)
            dialog.setContentView(dialogMainBinding.root)

            dialogMainBinding.report.setOnClickListener {
                sendAction(ReportConfirmAction(restaurant), fragment.randomizerViewModel)
                dialog.cancel()
            }
            dialogMainBinding.cancel.setOnClickListener { dialog.cancel() }
            dialogMainBinding.message.text = "Would you like to report ${restaurant.name} as permanently closed and add it to your blocked list?"
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.show()
        }
    }

}
