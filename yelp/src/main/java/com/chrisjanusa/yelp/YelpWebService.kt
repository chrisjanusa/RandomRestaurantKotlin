package com.chrisjanusa.yelp

import com.chrisjanusa.yelp.models.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpWebService {
    @GET("/search")
    suspend fun getBusinessSearchResults(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double
    ) : SearchResults
}