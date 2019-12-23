package com.chrisjanusa.randomizer.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.helpers.LocationHelper.LOCATION_ID
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import com.chrisjanusa.base_randomizer.BaseEvent


class LocationFailedEvent(private val locationSettingsTask: Task<LocationSettingsResponse>) : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        val exception = locationSettingsTask.exception
        if (exception is ResolvableApiException) {
            exception.startResolutionForResult(fragment.activity, LOCATION_ID)
        }
    }
}