package com.chrisjanusa.foursquare

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.text.DateFormat
import java.text.SimpleDateFormat


object FourSquareRepository {
    private const val FOURSQUARE_API_BASE_URL = "https://api.foursquare.com/v3/"

    suspend fun getBusinessSearchResults(
        latitude: Double,
        longitude: Double,
        term: String? = null,
        radius: Int = 8047,
        categories: String? = null,
        limit: Int = 50,
        minPrice: Int? = 1,
        maxPrice: Int? = 4,
        openNow: Boolean? = null,
        cursor: String? = null
    ) = getFourSquareWebService().getBusinessSearchResults(
        "$latitude,$longitude",
        query = term,
        radius = radius,
        categories = categories,
        limit = limit,
        minPrice = minPrice,
        maxPrice = maxPrice,
        openNow = openNow,
        fields = "categories,geocodes,distance,fsq_id,photos,hours,location,name,price,rating,stats,website,menu,closed_bucket,tips",
        cursor = cursor
    )

    suspend fun reportBusinessAsClosed(
        businessId: String
    ) = getFourSquareWebService().flagBusiness(
        businessId,
        "closed"
    )

    suspend fun getReportStatus(
        reportId: String
    ) = getFourSquareWebService().getFlagStatus(reportId)

    private fun getFourSquareWebService(): FourSquareWebService {
        val retrofit = Retrofit.Builder()
            .baseUrl(FOURSQUARE_API_BASE_URL)
            .addConverterFactory(getJacksonFactory())
            .build()
        return retrofit.create(FourSquareWebService::class.java)
    }

    private fun getJacksonFactory(): JacksonConverterFactory {
        val mapper = jacksonObjectMapper()
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        return JacksonConverterFactory.create(mapper)
    }
}