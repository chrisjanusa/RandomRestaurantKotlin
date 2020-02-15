package com.chrisjanusa.yelp.models

data class Businesses(
    val alias: String,
    val categories: List<Category> = ArrayList(),
    val coordinates: Coordinates,
    val distance: Double? = null,
    val id: String,
    val image_url: String? = null,
    val is_closed: Boolean? = null,
    val location: Location = Location(),
    val name: String? = null,
    val phone: String? = null,
    val price: String? = null,
    val rating: Int? = null,
    val review_count: Int? = null,
    val transactions: List<String> = ArrayList(),
    val url: String? = null
)