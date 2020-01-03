package com.chrisjanusa.randomizer

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.bottom_overlay.*
import kotlinx.android.synthetic.main.randomizer_frag.*
import androidx.annotation.DrawableRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.randomizer.location_gps.actions.PermissionReceivedAction
import com.chrisjanusa.randomizer.base.init.InitAction
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.location_gps.GpsHelper.PERMISSION_ID
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_boolean.BooleanFilterUIManager
import com.chrisjanusa.randomizer.filter_category.CategoryUIManager
import com.chrisjanusa.randomizer.filter_distance.DistanceUIManager
import com.chrisjanusa.randomizer.filter_price.PriceUIManager
import com.chrisjanusa.randomizer.filter_restriction.RestrictionUIManager
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultMapLocation
import com.chrisjanusa.randomizer.location_base.LocationUIManager
import com.chrisjanusa.randomizer.location_gps.GpsUIManager
import com.chrisjanusa.randomizer.location_gps.actions.PermissionDeniedAction
import com.chrisjanusa.randomizer.location_search.SearchUIManager
import kotlinx.coroutines.launch


class RandomizerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var map: GoogleMap? = null
    private var loc: LatLng? = null
    private val ZOOM_LEVEL = 16f
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
        randomizerViewModel = activity?.run {
            ViewModelProviders.of(this)[RandomizerViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
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

        mapView?.let {
            it.onCreate(null)
            it.onResume()
            it.getMapAsync(this)
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

    fun setMap(location: Location) {
        loc = location.latLang()
        setMap()
    }

    private fun setMap() {
        map?.run {
            icon = icon ?: getDefaultMarker()
            curr = loc?.takeUnless { curr == loc }?.also {
                clear()
                animateCamera(CameraUpdateFactory.newLatLngZoom(it, ZOOM_LEVEL))
                addMarker(
                    MarkerOptions()
                        .position(it)
                        .icon(icon)
                )
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
        loc = loc ?: defaultMapLocation.latLang()
        setMap()
    }

    private fun vectorToBitmap(@DrawableRes id: Int): BitmapDescriptor {
        val vectorDrawable =
            ResourcesCompat.getDrawable(resources, id, null) ?: return BitmapDescriptorFactory.defaultMarker()
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    fun getDefaultMarker(): BitmapDescriptor {
        return vectorToBitmap(R.drawable.marker)
    }

    fun randomize() {
        loc = defaultMapLocation.latLang()
        setMap()
    }

    fun Location.latLang(): LatLng {
        return LatLng(this.latitude, this.longitude)
    }
}