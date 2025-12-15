package com.kaku.data.datasource

import com.kaku.data.model.RestfulObjectApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteRestfulDataSource {
    @GET("objects")
    suspend fun getAllObjects(): List<RestfulObjectApiResponse>

    @GET("objects/{id}")
    suspend fun getObject(
        @Path("id") id: String,
    ): RestfulObjectApiResponse

    @GET("objects")
    suspend fun getObjectsByIds(
        @Query("id") ids: List<String>,
    ): List<RestfulObjectApiResponse>
}
