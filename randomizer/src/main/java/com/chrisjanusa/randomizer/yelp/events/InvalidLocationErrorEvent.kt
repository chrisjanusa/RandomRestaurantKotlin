package com.chrisjanusa.randomizer.yelp.events

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.location_search.actions.SearchGainFocusAction
import kotlinx.android.synthetic.main.error_dialog.*

class InvalidLocationErrorEvent : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        if (fragment !is RandomizerFragment) {
            return
        }
        fragment.context?.let { context ->
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.error_dialog)
            dialog.ok.setOnClickListener { dialog.cancel() }
            dialog.message.text = context.getString(R.string.location_not_set_error_message)
            dialog.title.text = context.getString(R.string.location_not_set_error_title)

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
