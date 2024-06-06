package com.chrisjanusa.foursquare

import com.chrisjanusa.foursquare.BuildConfig.FOURSQUARE_API_KEY
import com.chrisjanusa.foursquare.BuildConfig.FOURSQUARE_SERVICE_KEY
import com.chrisjanusa.foursquare.models.business_search.SearchResults
import com.chrisjanusa.foursquare.models.report.ReportResults
import com.chrisjanusa.foursquare.models.report.ReportStatusResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FourSquareWebService {

    @Headers("Authorization: $FOURSQUARE_API_KEY")
    @GET("places/search")
    suspend fun getBusinessSearchResults(
        @Query("ll") latitudeLongitude : String,
        @Query("query") query : String?,
        @Query("radius") radius : Int?,
        @Query("categories") categories : String?,
        @Query("limit") limit : Int?,
        @Query("min_price") minPrice : Int?,
        @Query("max_price") maxPrice : Int?,
        @Query("open_now") openNow : Boolean?,
        @Query("fields") fields : String?,
        @Query("cursor") cursor : String?,
    ) : Response<SearchResults>

    @Headers("Authorization: Bearer $FOURSQUARE_SERVICE_KEY")
    @POST("places/{fsq_id}/flag")
    suspend fun flagBusiness(
        @Path("fsq_id") businessId: String,
        @Query("problem") problem : String
    ) : Response<ReportResults>

    @Headers("Authorization: Bearer $FOURSQUARE_SERVICE_KEY")
    @POST("feedback/status")
    suspend fun getFlagStatus(
        @Query("proposed_edit_ids") flagId : String
    ) : Response<ReportStatusResults>
}