package com.chrisjanusa.randomizer

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import kotlinx.android.synthetic.main.randomizer_frag.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RandomizerFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var loc: Location? = null
    private val ZOOM_LEVEL = 16f
    private var curr: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.randomizer_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mapView != null) {
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }
    }

    fun setMap(location: Location) {
        loc = location
        if (mapView != null) {
            val latLong = LatLng(location.latitude, location.longitude)
            if (curr != latLong) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, ZOOM_LEVEL))
                map.addMarker(MarkerOptions().position(latLong))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        map = googleMap
        if (loc != null) {
            setMap(loc!!)
        }
    }
}