package com.kaku.domain.repositories

import com.kaku.domain.model.RestfulObjectData

interface RestfulRepository {
    suspend fun getAllItems(): List<RestfulObjectData>

    suspend fun getItemsById(ids: List<String>): List<RestfulObjectData>

    suspend fun getItem(id: String): RestfulObjectData
}
