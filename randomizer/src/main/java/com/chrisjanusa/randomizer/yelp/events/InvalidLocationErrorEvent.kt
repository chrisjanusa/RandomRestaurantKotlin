package com.chrisjanusa.randomizer.yelp.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.databinding.ErrorDialogBinding
import com.chrisjanusa.randomizer.location_search.actions.SearchGainFocusAction

class InvalidLocationErrorEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let { context ->
            val dialogMainBinding = ErrorDialogBinding.inflate(fragment.layoutInflater)
            val dialog = Dialog(context)
            dialog.setContentView(dialogMainBinding.root)

            dialogMainBinding.ok.setOnClickListener { dialog.cancel() }
            dialogMainBinding.message.text = context.getString(R.string.location_not_set_error_message)
            dialogMainBinding.title.text = context.getString(R.string.location_not_set_error_title)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.setOnCancelListener {
                sendAction(
                    SearchGainFocusAction(fragment.randomizerViewModel.state.value?.addressSearchString ?: ""),
                    fragment.randomizerViewModel
                )
            }
            dialog.show()
        }
    }

}
