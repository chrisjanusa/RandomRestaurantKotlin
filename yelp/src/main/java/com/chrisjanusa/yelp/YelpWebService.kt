package com.chrisjanusa.yelp

import com.chrisjanusa.yelp.BuildConfig.YELP_API_KEY
import com.chrisjanusa.yelp.models.SearchResults
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface YelpWebService {

    @Headers("Authorization: Bearer $YELP_API_KEY")
    @GET("search")
    suspend fun getBusinessSearchResults(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double,
        @Query("term") term : String?,
        @Query("radius") radius : Int?,
        @Query("categories") categories : String?,
        @Query("limit") limit : Int?,
        @Query("offset") offset : Int?,
        @Query("price") price : Int?,
        @Query("open_now") open_now : Boolean?
    ) : SearchResults
}