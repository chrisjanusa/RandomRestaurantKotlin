package com.chrisjanusa.randomizer.location_gps.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.location_gps.GpsHelper.LOCATION_ID
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task

class LocationFailedEvent(private val locationSettingsTask: Task<LocationSettingsResponse>) : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        val exception = locationSettingsTask.exception
        if (exception is ResolvableApiException) {
            fragment.activity?.let {activity ->
                exception.startResolutionForResult(activity, LOCATION_ID)
            }
        }
    }
}