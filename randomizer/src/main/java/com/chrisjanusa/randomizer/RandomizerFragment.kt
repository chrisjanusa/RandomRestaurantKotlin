package com.chrisjanusa.randomizer

import android.animation.LayoutTransition
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.bottom_overlay.*
import kotlinx.android.synthetic.main.randomizer_frag.*
import kotlinx.android.synthetic.main.search_card.*
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.chrisjanusa.randomizer.filter_base.actions.ClickSelectionFilterAction
import com.chrisjanusa.randomizer.filter_boolean.actions.FavoriteClickedAction
import com.chrisjanusa.randomizer.filter_boolean.actions.OpenNowClickedAction
import com.chrisjanusa.randomizer.location_gps.actions.GpsClickAction
import com.chrisjanusa.randomizer.location_gps.actions.PermissionReceivedAction
import com.chrisjanusa.randomizer.base.init.InitAction
import com.chrisjanusa.randomizer.filter_base.FilterHelper
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.defaultCategoryTitle
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.saveToDisplayString
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.distanceToDisplayString
import com.chrisjanusa.randomizer.location_gps.GpsHelper.PERMISSION_ID
import com.chrisjanusa.randomizer.filter_price.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.location_search.actions.*
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultMapLocation
import com.chrisjanusa.randomizer.location_base.LocationHelper.isDefault
import com.chrisjanusa.randomizer.location_gps.actions.PermissionDeniedAction
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.filters.*
import com.seatgeek.placesautocomplete.model.PlaceDetails
import com.seatgeek.placesautocomplete.DetailsCallback


class RandomizerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var map: GoogleMap? = null
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
        random.setOnClickListener { randomize() }
        current_location.setOnClickListener {
            sendAction(
                SearchGainFocusAction(
                    randomizerViewModel.state.value?.addressSearchString ?: ""
                ), randomizerViewModel
            )
        }
        gps_button.setOnClickListener { gpsChange() }
        open_now.setOnClickListener { clickOpenNow() }
        favorites_only.setOnClickListener { clickFavoritesOnly() }
        distance.setOnClickListener { clickSelectionFilter(Filter.Distance) }
        price.setOnClickListener { clickSelectionFilter(Filter.Price) }
        restrictions.setOnClickListener { clickSelectionFilter(Filter.Restriction) }
        categories.setOnClickListener { clickSelectionFilter(Filter.Category) }

        user_input.setOnPlaceSelectedListener { place ->
            sendAction(SearchFinishedAction(user_input.text.toString()), randomizerViewModel)
            user_input.getDetailsFor(place, object : DetailsCallback {
                override fun onSuccess(details: PlaceDetails) {
                    context?.let {
                        sendAction(LocationChosenAction(details, it), randomizerViewModel)
                    }
                }

                override fun onFailure(failure: Throwable) {

                }
            })

        }

        user_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendAction(SearchFinishedAction(user_input.text.toString()), randomizerViewModel)
                true
            } else {
                false
            }
        }

        user_input.setOnTouchListener { _, _ ->
            sendAction(
                SearchGainFocusAction(
                    randomizerViewModel.state.value?.addressSearchString ?: ""
                ), randomizerViewModel
            )
            true
        }

        // Detect when user_input has finished being expanded or closed
        search_bar.layoutTransition.addTransitionListener(object : LayoutTransition.TransitionListener {
            override fun startTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {

            }

            override fun endTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {
                if (view?.id == user_input.id && transitionType == LayoutTransition.CHANGE_APPEARING) {
                    sendAction(SearchOpenedAction(), randomizerViewModel)
                }
                if (view?.id == user_input.id && transitionType == LayoutTransition.CHANGE_DISAPPEARING) {
                    sendAction(SearchClosedAction(), randomizerViewModel)
                }
            }
        })

        back_icon.setOnClickListener {
            sendAction(
                SearchFinishedAction(user_input.text.toString()),
                randomizerViewModel
            )
        }
        search_icon.setOnClickListener {
            sendAction(
                SearchGainFocusAction(
                    randomizerViewModel.state.value?.addressSearchString ?: ""
                ), randomizerViewModel
            )
        }

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

    override fun onStop() {
        super.onStop()
        randomizerViewModel.state.value?.let {
            PreferenceHelper.saveState(
                it,
                activity?.getPreferences(Context.MODE_PRIVATE)
            )
        }
    }

    private val render = fun(newState: RandomizerState) {
        gps_button.isChecked = newState.gpsOn
        current_text.text = newState.locationText
        setMap(newState.location.takeUnless { isDefault(it) } ?: defaultMapLocation)
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
        categories.text =
            defaultCategoryTitle.takeUnless { categorySet.isNotEmpty() } ?: saveToDisplayString(categoryText)
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
                    sendAction(PermissionReceivedAction(it), randomizerViewModel)
                }
            } else {
                sendAction(PermissionDeniedAction(), randomizerViewModel)
            }
        }
    }


    private fun gpsChange() {
        activity?.let {
            sendAction(GpsClickAction(it), randomizerViewModel)
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