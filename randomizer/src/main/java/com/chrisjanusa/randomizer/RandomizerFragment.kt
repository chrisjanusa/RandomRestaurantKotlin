package com.chrisjanusa.randomizer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.android.synthetic.main.randomizer_frag.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RandomizerFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var loc: Location? = null
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
        icon = vectorToBitmap(R.drawable.ic_marker)
    }

    fun setMap(location: Location) {
        loc = location
        if (mapView != null) {
            if (icon == null){
                icon = vectorToBitmap(R.drawable.ic_marker)
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

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        map = googleMap
        if (loc != null) {
            setMap(loc!!)
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
}