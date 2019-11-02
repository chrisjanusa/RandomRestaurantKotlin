package com.chrisjanusa.randomizer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.bottom_overlay.*
import kotlinx.android.synthetic.main.randomizer_frag.*
import kotlinx.android.synthetic.main.search_card.*
import mumayank.com.airlocationlibrary.AirLocation
import android.widget.EditText

class RandomizerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private var loc: LatLng? = null
    private val ZOOM_LEVEL = 16f
    private var curr: LatLng? = null
    private var icon : BitmapDescriptor? = null
    private var airLocation: AirLocation? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.randomizer_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        random.setOnClickListener { randomize() }
        current.setOnClickListener { focusLocation() }
        gps_button.setOnClickListener { gpsChange() }
        if (mapView != null) {
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }
    }

    private fun gpsChange() {
        if(gps_button.isChecked) {
            airLocation = AirLocation(activity!!, true, true, object : AirLocation.Callbacks {
                override fun onSuccess(location: Location) {
                    current.text = Geocoder(context)
                        .getFromLocation(location.latitude,location.longitude,1)[0]
                    .locality
                    loc = location.latLang()
                    setMap()
                }

                override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

                }

            })
        }
        else{
            loc = LatLng(47.620422, -122.349358)
            setMap()
            current.text = "Space Needle"
        }
    }

    fun setMap(location: Location) {
        loc = location.latLang()
        setMap()
    }

    private fun setMap() {
        if (mapView != null) {
            if (icon == null){
                icon = getDefaultMarker()
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
        else{
            setCurrent()
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
        return false
    }

    fun getDefaultMarker() : BitmapDescriptor {
        return vectorToBitmap(R.drawable.marker)
    }

    fun randomize(){
        loc = LatLng(47.620422, -122.349358)
        setMap()
    }

    fun focusLocation(){
        user_input.showKeyboardAndFocus()
    }

    fun setCurrent() {
        airLocation = AirLocation(activity!!, true, true, object: AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), ZOOM_LEVEL))
            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

            }

        })
    }

    fun EditText.showKeyboardAndFocus(){
        this.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    fun EditText.loseFocus(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(this.windowToken, 0)
    }

    fun Location.latLang() : LatLng{
        return LatLng(this.latitude, this.longitude)
    }
}