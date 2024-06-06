package com.chrisjanusa.restaurant_base.restaurant_report.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.base.databinding.ReportStatusDialogBinding
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.models.ReportId
import com.chrisjanusa.foursquare.FourSquareRepository
import com.chrisjanusa.restaurant.Restaurant
import kotlinx.coroutines.launch

class ReportStatusEvent(private val restaurant: Restaurant, private val reportId: ReportId) : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let { context ->
            val dialogMainBinding = ReportStatusDialogBinding.inflate(fragment.layoutInflater)
            val dialog = Dialog(context)
            dialog.setContentView(dialogMainBinding.root)

            dialogMainBinding.ok.setOnClickListener {
                dialog.cancel()
            }
            dialogMainBinding.message.text = "Thank you for reporting ${restaurant.name} as permanently closed!"
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.show()
            fragment.lifecycleScope.launch {
                try {
                    val status = FourSquareRepository.getReportStatus(reportId).body()?.woes?.get(0)?.status
                    if (status.isNullOrBlank()) {
                        dialogMainBinding.status.text = "Error retrieving report status from Foursquare"
                        dialogMainBinding.statusShimmer.visibility = View.GONE
                        dialogMainBinding.status.visibility = View.VISIBLE
                    } else {
                        dialogMainBinding.status.text = status
                        dialogMainBinding.statusShimmer.visibility = View.GONE
                        dialogMainBinding.status.visibility = View.VISIBLE
                    }
                } catch (throwable: Throwable) {
                    dialogMainBinding.status.text = "Error retrieving report status from Foursquare"
                    dialogMainBinding.statusShimmer.visibility = View.GONE
                    dialogMainBinding.status.visibility = View.VISIBLE
                }
            }
        }
    }

}
