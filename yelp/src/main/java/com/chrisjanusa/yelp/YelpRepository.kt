package com.chrisjanusa.yelp

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


object YelpRepository {
    private const val YELP_API_BASE_URL = "https://api.yelp.com/v3/businesses/"

    suspend fun getBusinessSearchResults(
        latitude: Double,
        longitude: Double,
        term: String = "restaurants",
        radius: Int = 8047,
        categories: String? = null,
        limit: Int = 50,
        offset: Int? = null,
        price: String? = null,
        open_now: Boolean = true
    ) = getYelpWebService().getBusinessSearchResults(
        latitude,
        longitude,
        term,
        radius,
        categories,
        limit,
        offset,
        price,
        open_now
    )

    private fun getYelpWebService(): YelpWebService {
        val retrofit = Retrofit.Builder()
            .baseUrl(YELP_API_BASE_URL)
            .addConverterFactory(getJacksonFactory())
            .build()
        return retrofit.create(YelpWebService::class.java)
    }

    private fun getJacksonFactory(): JacksonConverterFactory {
        val mapper = jacksonObjectMapper()
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        return JacksonConverterFactory.create(mapper)
    }
}