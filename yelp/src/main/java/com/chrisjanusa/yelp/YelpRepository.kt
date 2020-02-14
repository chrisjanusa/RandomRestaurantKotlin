package com.chrisjanusa.yelp

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class YelpRepository {
    var client: YelpWebService = Retrofit.Builder()
        .baseUrl("https://api.yelp.com/v3/businesses")
        .addConverterFactory(JacksonConverterFactory.create())
        .build().create(YelpWebService::class.java)

    suspend fun getTodo(latitude : Double, longitude : Double) = client.getBusinessSearchResults(latitude, longitude)
}