package com.chrisjanusa.randomizer

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.init.InitAction
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.filter_boolean.BooleanFilterUIManager
import com.chrisjanusa.randomizer.filter_category.CategoryUIManager
import com.chrisjanusa.randomizer.filter_distance.DistanceUIManager
import com.chrisjanusa.randomizer.filter_price.PriceUIManager
import com.chrisjanusa.randomizer.filter_restriction.RestrictionUIManager
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultMapLocation
import com.chrisjanusa.randomizer.location_base.LocationHelper.latLang
import com.chrisjanusa.randomizer.location_base.LocationHelper.zoomLevel
import com.chrisjanusa.randomizer.location_base.LocationUIManager
import com.chrisjanusa.randomizer.location_base.LocationUIManager.getDefaultMarker
import com.chrisjanusa.randomizer.location_gps.GpsHelper.PERMISSION_ID
import com.chrisjanusa.randomizer.location_gps.GpsUIManager
import com.chrisjanusa.randomizer.location_gps.actions.PermissionDeniedAction
import com.chrisjanusa.randomizer.location_gps.actions.PermissionReceivedAction
import com.chrisjanusa.randomizer.location_search.SearchUIManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.bottom_overlay.*
import kotlinx.coroutines.launch


class RandomizerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var map: GoogleMap? = null
    private var curr: LatLng? = null
    private var icon: BitmapDescriptor? = null
    private lateinit var randomizerViewModel: RandomizerViewModel
    private val featureUIManagers = listOf(
        BooleanFilterUIManager,
        CategoryUIManager,
        DistanceUIManager,
        PriceUIManager,
        RestrictionUIManager,
        LocationUIManager,
        GpsUIManager,
        SearchUIManager
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomizerViewModel = activity?.run { ViewModelProviders.of(this)[RandomizerViewModel::class.java] }
            ?: throw Exception("Invalid Activity")
        sendAction(InitAction(activity), randomizerViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.randomizer_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (uiManager in featureUIManagers) {
            uiManager.init(randomizerViewModel, this)
        }

        random.setOnClickListener { randomize() }

        randomizerViewModel.state.observe(this, Observer<RandomizerState>(render))
        val frag = this
        lifecycleScope.launch {
            for (event in randomizerViewModel.eventChannel) {
                event.handleEvent(frag)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        randomizerViewModel.state.value?.let {
            PreferenceHelper.saveState(it, activity?.getPreferences(Context.MODE_PRIVATE))
        }
    }

    private val render = fun(newState: RandomizerState) {
        for (uiManager in featureUIManagers) {
            uiManager.render(newState, this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                activity?.let {
                    sendAction(PermissionReceivedAction(it), randomizerViewModel)
                }
            } else {
                sendAction(PermissionDeniedAction(), randomizerViewModel)
            }
        }
    }

    private fun setMap(location: LatLng, addMarker: Boolean) {
        map?.run {
            curr = location.takeUnless { curr == location }?.also {
                clear()
                animateCamera(CameraUpdateFactory.newLatLngZoom(it, zoomLevel))
                if (addMarker) {
                    addMarker(
                        MarkerOptions()
                            .position(it)
                            .icon(icon)
                    )
                }
            } ?: curr
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        map = googleMap
        map?.let {
            it.setOnMarkerClickListener(this)
            it.uiSettings.isMapToolbarEnabled = false
        }
        icon = getDefaultMarker(this)
        lifecycleScope.launch {
            for (update in randomizerViewModel.mapChannel) {
                setMap(update.location, update.addMarker)
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    private fun randomize() {
        setMap(defaultMapLocation.latLang(), true)
    }
}