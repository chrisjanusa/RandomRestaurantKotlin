package com.chrisjanusa.yelp

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper


object YelpRepository {
    private const val YELP_API_BASE_URL = "https://api.yelp.com/v3/businesses/"

    suspend fun getBusinessSearchResults(latitude : Double, longitude : Double) = getYelpWebService().getBusinessSearchResults(latitude, longitude)

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