package com.chrisjanusa.randomizer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.randomizer_frag.*

class RandomizerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private var loc: LatLng? = null
    private val ZOOM_LEVEL = 16f
    private var curr: LatLng? = null
    private var icon : BitmapDescriptor? = null

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
        icon = vectorToBitmap(R.drawable.marker)
    }

    fun setMap(location: Location) {
        loc = LatLng(location.latitude, location.longitude)
        if (mapView != null) {
            if (icon == null){
                icon = vectorToBitmap(R.drawable.marker)
            }
            val latLong = LatLng(location.latitude, location.longitude)
            if (curr != latLong) {
                map.clear()
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, ZOOM_LEVEL))
                map.addMarker(MarkerOptions()
                    .position(latLong)
                    .icon(icon))
            }
        }
    }

    private fun setMap() {
        if (mapView != null) {
            if (icon == null){
                icon = vectorToBitmap(R.drawable.marker)
            }
            if (curr != loc) {
                map.clear()
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, ZOOM_LEVEL))
                map.addMarker(MarkerOptions()
                    .position(loc!!)
                    .icon(icon))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        map = googleMap
        map.setOnMarkerClickListener(this)
        map.uiSettings.isMapToolbarEnabled = false
        if (loc != null) {
            setMap()
        }
    }

    private fun vectorToBitmap(@DrawableRes id : Int): BitmapDescriptor {
        val vectorDrawable: Drawable = ResourcesCompat.getDrawable(resources, id, null) ?: return BitmapDescriptorFactory.defaultMarker()
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //TODO: open the menu to open in other apps
        return false
    }

}