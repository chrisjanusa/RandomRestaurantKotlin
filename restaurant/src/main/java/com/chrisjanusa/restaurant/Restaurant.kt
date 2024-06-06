package com.chrisjanusa.restaurant

typealias RestaurantId = String

data class Restaurant(
    val name: String,
    val id: RestaurantId,
    val rating: Float?,
    val ratingCount: Long,
    val imageUrl: String? = null,
    val categories: List<Category>,
    val price: String?,
    val distance: Int?,
    val location: Location,
    val coordinates: Coordinates,
    val link: Link,
    val likelyClosed: Boolean,
//    val latestInteraction: Date?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Restaurant

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}