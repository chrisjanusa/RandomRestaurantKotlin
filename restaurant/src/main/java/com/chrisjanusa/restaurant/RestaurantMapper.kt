package com.chrisjanusa.restaurant

import com.chrisjanusa.foursquare.models.business_search.Photo
import com.chrisjanusa.foursquare.models.business_search.Geocodes
import java.util.Date

fun com.chrisjanusa.foursquare.models.business_search.Restaurant.toDomainModel(): Restaurant {
    // convert rating from 0.0 to 10.0 into a 5 star rating
    val rating = rating?.div(2)

    val imageUrl = photos.getOrNull(0)?.toPhotoImageUrl() ?: categories.getOrNull(0)?.icon?.toIconImageUrl()

    val link = website?.let { createWebsiteLink(it) } ?: createFourSquareLink(fsq_id)

//    val interactions = photos.mapNotNull { it.created_at } + tips.mapNotNull { it.created_at }

//    val latestInteraction = interactions.maxOrNull()
    return Restaurant(
        name = name,
        rating = rating?.toFloat(),
        imageUrl = imageUrl,
        ratingCount = stats?.total_ratings ?: 0,
        categories = categories.map { it.toDomainModel() },
        price = price?.let { "$".repeat(it) },
        distance = distance,
        location = location.toDomainModel(),
        coordinates = geocodes.toDomainModel(),
        link = link,
        id = fsq_id,
        likelyClosed = closed_bucket?.contains("Closed", ignoreCase = true) ?: false,
//        latestInteraction = latestInteraction
    )
}

//fun com.chrisjanusa.foursquare.models.business_search.Restaurant.toCloseTest(): CloseTest {
//
//    val interactions = photos.mapNotNull { it.created_at } + tips.mapNotNull { it.created_at }
//
//    val latestInteraction = interactions.maxOrNull()
//    return CloseTest(name, latestInteraction, closed_bucket ?: "Not Found")
//}

fun com.chrisjanusa.foursquare.models.business_search.Category.toDomainModel() = Category(name)

fun Photo.toPhotoImageUrl() = "${prefix}original$suffix"

fun Photo.toIconImageUrl() = "${prefix}bg_120$suffix"

fun com.chrisjanusa.foursquare.models.business_search.Location.toDomainModel() = Location(address, locality, region, formatted_address)

fun Geocodes.toDomainModel() = front_door?.toDomainModel() ?: main.toDomainModel()

fun com.chrisjanusa.foursquare.models.business_search.Coordinates.toDomainModel() = Coordinates(latitude, longitude)

//data class CloseTest(
//    val name: String,
//    val latestInteraction: Date?,
//    val closeBucket: String
//)