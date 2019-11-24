package com.chrisjanusa.randomizer.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.helpers.LocationHelper.LOCATION_ID
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task

class LocationEvent(val locationSettingsTask: Task<LocationSettingsResponse>) : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        val curr = fragment.context
        if (curr != null) {
            val exception = locationSettingsTask.exception
            if(exception is ResolvableApiException) {
                exception.startResolutionForResult(fragment.activity, LOCATION_ID)
            }
        }
    }

}