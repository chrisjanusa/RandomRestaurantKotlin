package com.chrisjanusa.randomizer

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.base_filters.fragment.filters.ClickSelectionFilterAction
import com.chrisjanusa.randomizer.actions.filter.favorites.FavoriteClickedAction
import com.chrisjanusa.randomizer.actions.filter.open_now.OpenNowClickedAction
import com.chrisjanusa.randomizer.actions.gpsActions.GpsClickAction
import com.chrisjanusa.randomizer.actions.gpsActions.PermissionReceivedAction
import com.chrisjanusa.randomizer.actions.init.InitAction
import com.chrisjanusa.randomizer.helpers.*
import com.chrisjanusa.randomizer.helpers.ActionHelper.sendAction
import com.chrisjanusa.randomizer.helpers.FilterHelper.Filter
import com.chrisjanusa.randomizer.helpers.CategoryHelper.Category
import com.chrisjanusa.randomizer.helpers.CategoryHelper.defaultCategoryTitle
import com.chrisjanusa.randomizer.helpers.CategoryHelper.saveToDisplayString
import com.chrisjanusa.randomizer.helpers.DistanceHelper.distanceToDisplayString
import com.chrisjanusa.randomizer.helpers.LocationHelper.PERMISSION_ID
import com.chrisjanusa.randomizer.helpers.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.helpers.RestrictionHelper.Restriction
import com.chrisjanusa.base_randomizer.RandomizerState
import com.chrisjanusa.base_randomizer.RandomizerViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.filters.*


class RandomizerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private var loc: LatLng? = null
    private val ZOOM_LEVEL = 16f
    private var curr: LatLng? = null
    private var icon: BitmapDescriptor? = null
    private lateinit var randomizerViewModel: RandomizerViewModel
    private val frag = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomizerViewModel = activity?.run {
            ViewModelProviders.of(this)[RandomizerViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
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
        random.setOnClickListener { randomize() }
        current.setOnClickListener { focusLocation() }
        gps_button.setOnClickListener { gpsChange() }
        open_now.setOnClickListener { clickOpenNow() }
        favorites_only.setOnClickListener { clickFavoritesOnly() }
        distance.setOnClickListener { clickSelectionFilter(Filter.Distance) }
        price.setOnClickListener { clickSelectionFilter(Filter.Price) }
        restrictions.setOnClickListener { clickSelectionFilter(Filter.Restriction) }
        categories.setOnClickListener { clickSelectionFilter(Filter.Category) }

        randomizerViewModel.state.observe(this, Observer<RandomizerState>(render))

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

    private fun clickFavoritesOnly() {
        sendAction(FavoriteClickedAction(), randomizerViewModel)
    }

    private fun clickSelectionFilter(filter: Filter) {
        sendAction(ClickSelectionFilterAction(filter), randomizerViewModel)
    }

    private fun clickOpenNow() {
        sendAction(OpenNowClickedAction(), randomizerViewModel)
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitAction(activity), randomizerViewModel)
    }

    override fun onPause() {
        super.onPause()
        randomizerViewModel.state.value?.let {
            PreferenceHelper.saveState(
                it,
                activity?.getPreferences(Context.MODE_PRIVATE)
            )
        }
    }

    private val render = fun(newState: RandomizerState) {
        gps_button.isChecked = newState.gpsOn
        current.text = newState.locationText
        newState.location?.let {
            setMap(it)
        }
        renderPriceButton(newState.priceText)
        renderRestrictionButton(newState.restriction)
        renderDistanceButton(newState.maxMilesSelected)
        renderBooleanButton(newState.openNowSelected, open_now)
        renderBooleanButton(newState.favoriteOnlySelected, favorites_only)
        renderCategoryButton(newState.categoryString, newState.categorySet)
    }

    private fun renderRestrictionButton(restriction: Restriction) {
        val selected = !RestrictionHelper.isDefault(restriction)
        restrictions.text = if (selected) restriction.display else RestrictionHelper.defaultRestrictionTitle
        context?.let { FilterHelper.renderFilterStyle(restrictions, selected, it) }
    }

    private fun renderDistanceButton(maxMilesSelected: Float) {
        val selected = !DistanceHelper.isDefault(maxMilesSelected)
        distance.text = if (selected) distanceToDisplayString(maxMilesSelected) else DistanceHelper.defaultDistanceTitle
        context?.let { FilterHelper.renderFilterStyle(distance, selected, it) }
    }

    private fun renderPriceButton(priceText: String) {
        val selected = priceText != defaultPriceTitle
        price.text = priceText
        context?.let { FilterHelper.renderFilterStyle(price, selected, it) }
    }

    private fun renderCategoryButton(categoryText: String, categorySet: HashSet<Category>) {
        val selected = categorySet.isNotEmpty()
        categories.text = defaultCategoryTitle.takeUnless { categorySet.isNotEmpty() } ?: saveToDisplayString(categoryText)
        context?.let { FilterHelper.renderFilterStyle(categories, selected, it) }
    }

    private fun renderBooleanButton(
        selected: Boolean,
        button: MaterialButton
    ) {
        context?.let {
            if (selected) {
                button.setBackgroundColor(ContextCompat.getColor(it, R.color.filter_background_selected))
                button.setTextColor(ContextCompat.getColor(it, R.color.filter_text_selected))
            } else {
                button.setBackgroundColor(ContextCompat.getColor(it, R.color.filter_background_not_selected))
                button.setTextColor(ContextCompat.getColor(it, R.color.filter_text_not_selected))
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                activity?.let {
                    sendAction(PermissionReceivedAction(it, randomizerViewModel), randomizerViewModel)
                }
            }
        }
    }


    private fun gpsChange() {
        activity?.let {
            sendAction(GpsClickAction(it, randomizerViewModel), randomizerViewModel)
        }

    }

    fun setMap(location: Location) {
        loc = location.latLang()
        setMap()
    }

    private fun setMap() {
        if (mapView != null) {
            icon = icon ?: getDefaultMarker()
            curr = loc?.takeUnless { curr == loc }?.also {
                map.clear()
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, ZOOM_LEVEL))
                map.addMarker(
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
        map.setOnMarkerClickListener(this)
        map.uiSettings.isMapToolbarEnabled = false
        loc = loc ?: LatLng(47.620422, -122.349358)
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
        loc = LatLng(47.620422, -122.349358)
        setMap()
    }

    fun focusLocation() {
        user_input.showKeyboardAndFocus()
    }

    fun EditText.showKeyboardAndFocus() {
        this.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.let {
            it.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun EditText.loseFocus() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.let {
            it.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }

    fun Location.latLang(): LatLng {
        return LatLng(this.latitude, this.longitude)
    }
}