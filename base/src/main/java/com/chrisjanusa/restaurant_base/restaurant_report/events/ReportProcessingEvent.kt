package com.chrisjanusa.restaurant_base.restaurant_report.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import com.chrisjanusa.base.databinding.ReportStatusDialogBinding
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.restaurant.Restaurant

class ReportProcessingEvent(private val restaurant: Restaurant) : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let { context ->
            val dialogMainBinding = ReportStatusDialogBinding.inflate(fragment.layoutInflater)
            val dialog = Dialog(context)
            dialog.setContentView(dialogMainBinding.root)

            dialogMainBinding.ok.setOnClickListener { dialog.cancel() }

            dialogMainBinding.message.text = "Thank you for reporting ${restaurant.name} as permanently closed!"
            dialogMainBinding.status.text = "Foursquare is currently processing your report"
            dialogMainBinding.statusShimmer.visibility = View.GONE
            dialogMainBinding.status.visibility = View.VISIBLE
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.show()
        }
    }

}
