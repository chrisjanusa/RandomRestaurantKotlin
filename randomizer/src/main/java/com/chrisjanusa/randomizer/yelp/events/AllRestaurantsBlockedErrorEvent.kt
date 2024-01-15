package com.chrisjanusa.randomizer.yelp.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment

class AllRestaurantsBlockedErrorEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let { context ->
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.error_dialog)
            // TODO: Synthetics
//            dialog.ok.setOnClickListener { dialog.cancel() }
//            dialog.message.text = context.getString(R.string.all_restaurants_blocked_error_message)
//            dialog.title.text = context.getString(R.string.all_restaurants_blocked_error_title)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.show()
        }
    }

}
