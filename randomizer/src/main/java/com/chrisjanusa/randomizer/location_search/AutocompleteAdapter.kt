package com.chrisjanusa.randomizer.location_search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chrisjanusa.randomizer.R
import com.seatgeek.placesautocomplete.PlacesApi
import com.seatgeek.placesautocomplete.adapter.AbstractPlacesAutocompleteAdapter
import com.seatgeek.placesautocomplete.history.AutocompleteHistoryManager
import com.seatgeek.placesautocomplete.model.AutocompleteResultType
import com.seatgeek.placesautocomplete.model.Place

class AutocompleteAdapter(
    context: Context,
    api: PlacesApi,
    resultType: AutocompleteResultType,
    history: AutocompleteHistoryManager
) : AbstractPlacesAutocompleteAdapter(context, api, resultType, history) {

    override fun newView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.places_autocomplete_item, parent, false)
    }

    override fun bindView(view: View, item: Place) {
        (view as TextView).text = item.description
    }
}