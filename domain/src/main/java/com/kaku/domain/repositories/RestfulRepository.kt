package com.kaku.domain.repositories

import com.kaku.domain.model.RestfulObjectData

interface RestfulRepository {
    suspend fun getAllItems(): Result<List<RestfulObjectData>>

    suspend fun getItemsById(ids: List<String>): Result<List<RestfulObjectData>>

    suspend fun getItem(id: String): Result<RestfulObjectData>
}
