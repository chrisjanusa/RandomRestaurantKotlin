package com.chrisjanusa.randomizer.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.chrisjanusa.randomizer.actions.gpsActions.LocationReceivedAction
import com.chrisjanusa.randomizer.events.LocationFailedEvent
import com.chrisjanusa.randomizer.events.PermissionEvent
import com.chrisjanusa.randomizer.helpers.ActionHelper.sendAction
import com.chrisjanusa.randomizer.helpers.ActionHelper.sendEvent
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

object LocationHelper {
    const val PERMISSION_ID = 42
    const val LOCATION_ID = 7

    @SuppressLint("MissingPermission")
    fun requestLocation(activity: Activity, randomizerViewModel: RandomizerViewModel) {
        val context: Context = activity.applicationContext ?: return
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (checkPermissions(context)) {
            mFusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                val location: Location? = task.result
                if (location == null) {
                    val request = requestNewLocationRequest()
                    isLocationEnabled(context, request).addOnCompleteListener(activity) { settingsTask ->
                        if (settingsTask.isSuccessful) {
                            makeLocationRequest(request, mFusedLocationClient, object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    sendAction(
                                        LocationReceivedAction(locationResult.lastLocation, context),
                                        randomizerViewModel
                                    )
                                }
                            })

                        } else {
                            sendEvent(LocationFailedEvent(settingsTask), randomizerViewModel)
                        }
                    }
                } else {
                    sendAction(LocationReceivedAction(location, context), randomizerViewModel)
                }
            }
        } else {
            sendEvent(getLocationPermissionEvent(), randomizerViewModel)
        }
    }


    private fun requestNewLocationRequest(): LocationRequest {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        return mLocationRequest
    }

    @SuppressLint("MissingPermission")
    private fun makeLocationRequest(
        locationRequest: LocationRequest, mFusedLocationClient: FusedLocationProviderClient,
        callback: LocationCallback
    ) {
        mFusedLocationClient.requestLocationUpdates(
            locationRequest, callback,
            Looper.myLooper()
        )
    }


    private fun isLocationEnabled(
        context: Context,
        request: LocationRequest
    ): Task<LocationSettingsResponse> {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(request)
        val client: SettingsClient = LocationServices.getSettingsClient(context)
        return client.checkLocationSettings(builder.build())
    }

    private fun checkPermissions(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    fun getLocationPermissionEvent(): PermissionEvent {
        return PermissionEvent(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }
}