package com.chrisjanusa.base.models.enums

sealed class Filter {
    object Price : Filter()
    object Cuisine : Filter()
    object Distance : Filter()
    object Diet : Filter()
    object Rating : Filter()
}