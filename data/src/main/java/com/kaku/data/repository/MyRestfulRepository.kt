package com.kaku.data.repository

import com.kaku.data.datasource.RemoteRestfulDataSource
import com.kaku.data.mapper.RestfulDataMapper
import com.kaku.domain.model.RestfulObjectData
import com.kaku.domain.repositories.RestfulRepository
import com.kaku.network.extensions.callApi
import javax.inject.Inject

class MyRestfulRepository @Inject constructor(
    private val remoteDataSource: RemoteRestfulDataSource,
) : RestfulRepository {
    override suspend fun getAllItems(): Result<List<RestfulObjectData>> = callApi {
        remoteDataSource.getAllObjects()
            .map(RestfulDataMapper::toRestfulObjectData)
    }

    override suspend fun getItemsById(ids: List<String>): Result<List<RestfulObjectData>> = callApi {
        remoteDataSource.getObjectsByIds(ids)
            .map(RestfulDataMapper::toRestfulObjectData)
    }

    override suspend fun getItem(id: String): Result<RestfulObjectData> =
        callApi { remoteDataSource.getObject(id) }
            .mapCatching(RestfulDataMapper::toRestfulObjectData)
}
