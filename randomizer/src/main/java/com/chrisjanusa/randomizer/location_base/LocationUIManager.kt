package com.chrisjanusa.randomizer.location_base

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.location_search.actions.SearchGainFocusAction
import com.google.android.libraries.maps.model.BitmapDescriptor
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import kotlinx.android.synthetic.main.search_card.*

object LocationUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: Fragment) {
        if (fragment !is RandomizerFragment) {
            return
        }
        fragment.run {
            current_location.setOnClickListener {
                sendAction(
                    SearchGainFocusAction(randomizerViewModel.state.value?.addressSearchString ?: ""),
                    randomizerViewModel
                )
            }

            mapView?.let {
                it.onCreate(null)
                it.onResume()
                it.getMapAsync(this)
            }
        }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {
        fragment.run {
            current_text.text = state.locationText
        }
    }

    fun getDefaultMarker(fragment: RandomizerFragment): BitmapDescriptor {
        return vectorToBitmap(R.drawable.marker, fragment)
    }

    private fun vectorToBitmap(@DrawableRes id: Int, fragment: RandomizerFragment): BitmapDescriptor {
        val vectorDrawable =
            ResourcesCompat.getDrawable(fragment.resources, id, null) ?: return BitmapDescriptorFactory.defaultMarker()
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}