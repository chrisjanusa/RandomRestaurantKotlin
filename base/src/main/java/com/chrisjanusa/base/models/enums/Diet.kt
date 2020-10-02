package com.chrisjanusa.base.models.enums

import com.chrisjanusa.base.models.defaultDietTitle

sealed class Diet(val identifier: String, val display: String) {
    object Vegan : Diet("vegan", "Vegan")
    object Vegetarian : Diet("vegetarian", "Vegetarian")
    object Halal : Diet("halal", "Halal")
    object Kosher : Diet("kosher", "Kosher")
    object None : Diet("none", defaultDietTitle)
}