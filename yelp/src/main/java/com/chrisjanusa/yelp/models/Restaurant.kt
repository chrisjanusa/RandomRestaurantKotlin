package com.chrisjanusa.yelp.models

data class Restaurant(
    val alias: String,
    val categories: List<Category> = ArrayList(),
    val coordinates: Coordinates,
    val distance: Double? = null,
    val id: String,
    val url: String,
    val image_url: String? = null,
    val is_closed: Boolean? = null,
    val location: Location = Location(),
    val name: String,
    val price: String? = null,
    val rating: Float? = null,
    val review_count: Int? = null
)