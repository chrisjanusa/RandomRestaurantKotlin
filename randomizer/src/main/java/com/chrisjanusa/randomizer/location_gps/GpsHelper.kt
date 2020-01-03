package com.chrisjanusa.randomizer.location_gps

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.chrisjanusa.randomizer.location_gps.events.LocationFailedEvent
import com.chrisjanusa.randomizer.location_gps.events.PermissionEvent
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendEvent
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendUpdate
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLocationText
import com.chrisjanusa.randomizer.location_base.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationTextUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationUpdater
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.channels.Channel

object GpsHelper {
    const val PERMISSION_ID = 42
    const val LOCATION_ID = 7

    private val coarsePermission
        get() = Manifest.permission.ACCESS_COARSE_LOCATION

    private val finePermission
        get() = Manifest.permission.ACCESS_FINE_LOCATION

    private val locationPermissionEvent: PermissionEvent
        get() = PermissionEvent(
            arrayOf(coarsePermission, finePermission),
            PERMISSION_ID
        )

    private val newLocationRequest: LocationRequest
        get() {
            val mLocationRequest = LocationRequest()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.interval = 0
            mLocationRequest.fastestInterval = 0
            mLocationRequest.numUpdates = 1

            return mLocationRequest
        }

    // Suppress since it is check in the if
    @SuppressLint("MissingPermission")
    fun requestLocation(activity: Activity, updateChannel: Channel<BaseUpdater>, eventChannel: Channel<BaseEvent>) {
        val context: Context = activity.applicationContext ?: return
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (checkLocationPermissions(context)) {
            mFusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                val location: Location? = task.result
                if (location == null) {
                    makeLocationRequest(activity, context, mFusedLocationClient, updateChannel, eventChannel)
                } else {
                    receiveLocation(context, location, updateChannel)
                }
            }
        } else {
            sendEvent(locationPermissionEvent, eventChannel)
        }
    }

    private fun receiveLocation(context: Context, location: Location, updateChannel: Channel<BaseUpdater>) {
        val locationName = Geocoder(context)
            .getFromLocation(location.latitude, location.longitude, 1)[0]
            .locality
        sendUpdate(LocationUpdater(locationName, location), updateChannel)
    }

    // Is not called unless permissions have been checked
    @SuppressLint("MissingPermission")
    private fun makeLocationRequest(
        activity: Activity,
        context: Context,
        mFusedLocationClient: FusedLocationProviderClient,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        val request = newLocationRequest
        isLocationEnabled(context, request)
            .addOnCompleteListener(activity) { settingsTask ->
                if (settingsTask.isSuccessful) {
                    mFusedLocationClient
                        .requestLocationUpdates(request, getLocationCallback(context, updateChannel), Looper.myLooper())
                } else {
                    sendUpdate(LocationTextUpdater(defaultLocationText), updateChannel)
                    sendUpdate(GpsStatusUpdater(false), updateChannel)
                    sendEvent(LocationFailedEvent(settingsTask), eventChannel)
                }
            }
    }

    private fun isLocationEnabled(context: Context, request: LocationRequest): Task<LocationSettingsResponse> {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(request)
        val client: SettingsClient = LocationServices.getSettingsClient(context)
        return client.checkLocationSettings(builder.build())
    }

    private fun getLocationCallback(context: Context, updateChannel: Channel<BaseUpdater>) : LocationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                receiveLocation(context, locationResult.lastLocation, updateChannel)
            }
        }

    private fun checkLocationPermissions(context: Context) =
        checkPermission(context, coarsePermission) && checkPermission(context, finePermission)

    private fun checkPermission(context: Context, permission: String) =
        ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

}