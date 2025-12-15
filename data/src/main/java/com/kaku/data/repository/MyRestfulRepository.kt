package com.kaku.data.repository

import com.kaku.data.datasource.RemoteRestfulDataSource
import com.kaku.data.mapper.RestfulDataMapper
import com.kaku.domain.model.RestfulObjectData
import com.kaku.domain.repositories.RestfulRepository
import javax.inject.Inject

class MyRestfulRepository @Inject constructor(
    private val remoteDataSource: RemoteRestfulDataSource,
) : RestfulRepository {
    override suspend fun getAllItems(): List<RestfulObjectData> =
        remoteDataSource
            .getAllObjects()
            .map(RestfulDataMapper::toRestfulObjectData)

    override suspend fun getItemsById(ids: List<String>): List<RestfulObjectData> =
        remoteDataSource
            .getObjectsByIds(ids)
            .map(RestfulDataMapper::toRestfulObjectData)

    override suspend fun getItem(id: String): RestfulObjectData =
        remoteDataSource
            .getObject(id)
            .run(RestfulDataMapper::toRestfulObjectData)
}
