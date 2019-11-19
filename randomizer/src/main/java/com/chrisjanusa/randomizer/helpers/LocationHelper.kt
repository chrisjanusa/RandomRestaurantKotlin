package com.chrisjanusa.randomizer.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.actions.gpsActions.LocationReceivedAction
import com.chrisjanusa.randomizer.actions.permissionActions.RequestLocationSettingAction
import com.chrisjanusa.randomizer.actions.permissionActions.RequestPermissionAction
import com.chrisjanusa.randomizer.helpers.ActionHelper.sendAction
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import com.google.android.gms.location.*

object LocationHelper {
    const val PERMISSION_ID = 42

    @SuppressLint("MissingPermission")
    fun requestLocation(activity: Activity, randomizerViewModel: RandomizerViewModel) {
        val context: Context = activity.applicationContext ?: return
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (checkPermissions(context)) {
            if (isLocationEnabled(context)) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData(context, mFusedLocationClient, object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                sendAction(
                                    LocationReceivedAction(locationResult.lastLocation, context),
                                    randomizerViewModel
                                )
                            }
                        })
                    } else {
                        sendAction(LocationReceivedAction(location, context), randomizerViewModel)
                    }
                }
            } else {
                sendAction(RequestLocationSettingAction(), randomizerViewModel)
            }
        } else {
            sendAction(RequestPermissionAction(), randomizerViewModel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(
        context: Context,
        mFusedLocationClient: FusedLocationProviderClient,
        callback: LocationCallback
    ) {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, callback,
            Looper.myLooper()
        )
    }


    private fun isLocationEnabled(context: Context): Boolean {
        var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
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

    fun requestPermissions(frag: Fragment) {
        frag.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }
}