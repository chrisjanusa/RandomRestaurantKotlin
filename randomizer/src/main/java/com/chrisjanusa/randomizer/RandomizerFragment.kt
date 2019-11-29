package com.chrisjanusa.randomizer

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
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
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.randomizer.actions.gpsActions.GpsClickAction
import com.chrisjanusa.randomizer.actions.gpsActions.PermissionReceivedAction
import com.chrisjanusa.randomizer.actions.init.InitAction
import com.chrisjanusa.randomizer.helpers.ActionHelper.sendAction
import com.chrisjanusa.randomizer.helpers.LocationHelper.PERMISSION_ID
import com.chrisjanusa.randomizer.helpers.PreferenceHelper
import com.chrisjanusa.randomizer.models.RandomizerState
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import kotlinx.coroutines.launch

class RandomizerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private var loc: LatLng? = null
    private val ZOOM_LEVEL = 16f
    private var curr: LatLng? = null
    private var icon: BitmapDescriptor? = null
    private val randomizerViewModel = RandomizerViewModel()
    private val frag = this

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

        randomizerViewModel.state.observe(this, Observer<RandomizerState>(render))

        lifecycleScope.launch {
            for (event in randomizerViewModel.eventChannel) {
                event.handleEvent(frag)
            }
        }

        if (mapView != null) {
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }
    }

    override fun onResume() {
        super.onResume()
        val preferenceData = PreferenceHelper.retrieveState(activity?.getPreferences(Context.MODE_PRIVATE))
        sendAction(InitAction(preferenceData), randomizerViewModel)
    }

    override fun onPause() {
        super.onPause()
        PreferenceHelper.saveState(randomizerViewModel.state.value!!, activity?.getPreferences(Context.MODE_PRIVATE))
    }

    val render = fun(newState: RandomizerState) {
        gps_button.isChecked = newState.gpsOn
        current.text = newState.locationText
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                sendAction(
                    PermissionReceivedAction(
                        activity!!,
                        randomizerViewModel
                    ), randomizerViewModel)
            }
        }
    }


    private fun gpsChange() {
        sendAction(GpsClickAction(activity!!, randomizerViewModel), randomizerViewModel)
    }

    fun setMap(location: Location) {
        loc = location.latLang()
        setMap()
    }

    private fun setMap() {
        if (mapView != null) {
            if (icon == null) {
                icon = getDefaultMarker()
            }
            if (curr != loc) {
                map.clear()
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, ZOOM_LEVEL))
                map.addMarker(
                    MarkerOptions()
                        .position(loc!!)
                        .icon(icon)
                )
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
        } else {
            loc = LatLng(47.620422, -122.349358)
            setMap()
        }
    }

    private fun vectorToBitmap(@DrawableRes id: Int): BitmapDescriptor {
        val vectorDrawable: Drawable =
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
        loc = LatLng(47.620422, -122.349358)
        setMap()
    }

    fun focusLocation() {
        user_input.showKeyboardAndFocus()
    }

    fun EditText.showKeyboardAndFocus() {
        this.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    fun EditText.loseFocus() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(this.windowToken, 0)
    }

    fun Location.latLang(): LatLng {
        return LatLng(this.latitude, this.longitude)
    }
}