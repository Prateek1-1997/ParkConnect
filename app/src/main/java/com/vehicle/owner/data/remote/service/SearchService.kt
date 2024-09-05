package com.vehicle.owner.data.remote.service

import com.vehicle.owner.data.response.BaseDto
import com.vehicle.owner.data.response.VehicleData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/api/search")
    suspend fun getSearchDetails(
        @Query("query") query: String,
    ): Response<BaseDto<VehicleData>>


}