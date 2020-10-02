package com.chrisjanusa.randomizer

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.base.CommunicationHelper.getViewModel
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.init.InitAction
import com.chrisjanusa.randomizer.init.InitMapAction
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.filter_boolean.BooleanFilterUIManager
import com.chrisjanusa.randomizer.filter_cuisine.CuisineUIManager
import com.chrisjanusa.randomizer.filter_diet.DietUIManager
import com.chrisjanusa.randomizer.filter_distance.DistanceUIManager
import com.chrisjanusa.randomizer.filter_price.PriceUIManager
import com.chrisjanusa.randomizer.location_base.LocationHelper.cameraSpeed
import com.chrisjanusa.randomizer.location_base.LocationHelper.zoomLevel
import com.chrisjanusa.randomizer.location_base.LocationUIManager
import com.chrisjanusa.randomizer.location_base.LocationUIManager.getDefaultMarker
import com.chrisjanusa.randomizer.location_gps.GpsHelper.PERMISSION_ID
import com.chrisjanusa.randomizer.location_gps.GpsUIManager
import com.chrisjanusa.randomizer.location_gps.actions.PermissionDeniedAction
import com.chrisjanusa.randomizer.location_gps.actions.PermissionReceivedAction
import com.chrisjanusa.randomizer.location_search.SearchUIManager
import com.chrisjanusa.randomizer.yelp.YelpUIManager
import com.google.android.libraries.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.model.BitmapDescriptor
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.randomizer_frag.*
import kotlinx.coroutines.launch

class RandomizerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var icon: BitmapDescriptor
    var mapView : MapView? = null
    val randomizerViewModel: RandomizerViewModel by lazy {
        activity?.let { getViewModel(it) } ?: throw Exception("Invalid Activity")
    }
    private val render = fun(newState: RandomizerState) {
        for (uiManager in featureUIManagers) {
            uiManager.render(newState, this)
        }
    }
    private val featureUIManagers = listOf(
        LocationUIManager,
        BooleanFilterUIManager,
        CuisineUIManager,
        DistanceUIManager,
        PriceUIManager,
        DietUIManager,
        GpsUIManager,
        SearchUIManager,
        YelpUIManager
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        randomizerViewModel.state.observe(viewLifecycleOwner, Observer<RandomizerState>(render))
        sendAction(InitAction(activity), randomizerViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.randomizer_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = MapView(activity?.applicationContext)
        mapWrapper.addView(mapView)
        for (uiManager in featureUIManagers) {
            uiManager.init(randomizerViewModel, this)
        }

        val frag = this
        lifecycleScope.launch {
            for (event in randomizerViewModel.eventChannel) {
                event.handleEvent(frag)
            }
        }
    }


    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        randomizerViewModel.state.value?.let {
            PreferenceHelper.saveState(it, activity?.getPreferences(Context.MODE_PRIVATE))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        map.isMyLocationEnabled = false
        mapView?.onDestroy()
        mapWrapper.removeAllViews()
        randomizerViewModel.state.value?.lastCacheUpdateJob?.cancel()
        mapView = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                activity?.let { sendAction(PermissionReceivedAction(it), randomizerViewModel) }
            } else {
                sendAction(PermissionDeniedAction(), randomizerViewModel)
            }
        }
    }

    private fun setMap(lat: Double, lng: Double, addMarker: Boolean) {
        val location = LatLng(lat, lng)
        map.run {
            clear()
            animateCamera(newLatLngZoom(location, zoomLevel), cameraSpeed, null)
            if (addMarker) {
                val marker = MarkerOptions()
                    .position(location)
                    .icon(icon)
                addMarker(marker)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        sendAction(InitMapAction(), randomizerViewModel)
        map = googleMap
        icon = getDefaultMarker(this)

        map.setOnMarkerClickListener(this)
        map.uiSettings?.isMapToolbarEnabled = false

        lifecycleScope.launch {
            for (update in randomizerViewModel.mapChannel) {
                setMap(update.lat, update.lng, update.addMarker)
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }
}
