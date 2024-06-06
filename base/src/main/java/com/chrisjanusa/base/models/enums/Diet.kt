package com.chrisjanusa.base.models.enums

import com.chrisjanusa.base.models.defaultDietTitle

sealed class Diet(val identifier: String, val display: String, val foursquare: String) {
    data object Vegan : Diet("vegan", "Vegan", "13377")
    data object Vegetarian : Diet("vegetarian", "Vegetarian", "13377")
    data object Halal : Diet("halal", "Halal", "13191")
    data object Kosher : Diet("kosher", "Kosher", "13287")
    data object None : Diet("none", defaultDietTitle, "")
}