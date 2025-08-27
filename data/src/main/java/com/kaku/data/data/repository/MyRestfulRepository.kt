package com.kaku.data.data.repository

import com.kaku.data.data.datasource.RemoteRestfulDataSource
import com.kaku.data.data.mapper.toData
import com.kaku.domain.model.RestfulObjectData
import com.kaku.domain.repositories.RestfulRepository
import javax.inject.Inject

class MyRestfulRepository @Inject constructor(
    private val remoteDataSource: RemoteRestfulDataSource,
) : RestfulRepository {

    override suspend fun getAllItems(): List<RestfulObjectData> {
        return remoteDataSource.getAllObjects()
            .map { it.toData() }
    }

    override suspend fun getItemsById(ids: List<String>): List<RestfulObjectData> {
        return remoteDataSource.getObjectsByIds(ids)
            .map { it.toData() }
    }

    override suspend fun getItem(id: String): RestfulObjectData {
        return remoteDataSource.getObject(id)
            .toData()
    }
}
