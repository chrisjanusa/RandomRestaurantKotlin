package com.chrisjanusa.restaurant

data class Location(
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val formattedAddress: String? = null
) {
    fun toAddress(): String {
        formattedAddress?.takeUnless { it.isBlank() }?.let { return formattedAddress }
        val addressComponents = mutableListOf<String>()
        address?.takeUnless { it.isBlank() }?.let { addressComponents.add(address) }
        city?.takeUnless { it.isBlank() }?.let { addressComponents.add(city) }
        state?.takeUnless { it.isBlank() }?.let { addressComponents.add(state) }
        return addressComponents.joinToString()
    }
}