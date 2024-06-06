package com.chrisjanusa.foursquare.models.business_search

import java.util.Date

data class Photo(
    val prefix: String,
    val suffix: String,
    val created_at: Date?
)