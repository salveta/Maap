package com.salvaperez.maaps.data.api

import com.salvaperez.maaps.data.entity.TransportsEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MaapsApi {

    @Headers("Accept: application/json")
    @GET("{city}/resources")
    suspend fun getTransports(
        @Path("city") city: String = "lisboa",
        @Query("lowerLeftLatLon") lowerLeftLatLon: String = "38.711046,-9.160096",
        @Query("upperRightLatLon") upperRightLatLon: String = "38.739429,-9.137115"): List<TransportsEntity>
}