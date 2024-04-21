package com.chrisjanusa.randomizer.location_base

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.location_search.actions.SearchGainFocusAction
import com.google.android.libraries.maps.model.BitmapDescriptor
import com.google.android.libraries.maps.model.BitmapDescriptorFactory

object LocationUIManager : FeatureUIManager {


    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                baseFragmentDetails.binding.locationContainer.searchCard.currentLocation.setOnClickListener {
                    sendAction(
                        SearchGainFocusAction(randomizerViewModel.state.value?.addressSearchString ?: ""),
                        randomizerViewModel
                    )
                }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.binding.locationContainer.searchCard.currentText.text = state.locationText
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