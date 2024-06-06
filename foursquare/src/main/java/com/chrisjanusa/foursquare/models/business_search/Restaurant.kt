package com.chrisjanusa.foursquare.models.business_search

data class Restaurant(
    val categories: List<Category> = ArrayList(),
    val geocodes: Geocodes,
    val distance: Int?,
    val fsq_id: String,
    val photos: List<Photo> = ArrayList(),
    val hours: Hours? = null,
    val location: Location = Location(),
    val name: String,
    val price: Int? = null,
    val rating: Double? = null,
    val website: String? = null,
    val menu: String? = null,
    val stats: Stats? = null,
    val closed_bucket: String?,
    val tips: List<Tip> = ArrayList()
) {
    override fun hashCode(): Int {
        return fsq_id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Restaurant) {
            fsq_id == other.fsq_id
        } else {
            false
        }
    }
}