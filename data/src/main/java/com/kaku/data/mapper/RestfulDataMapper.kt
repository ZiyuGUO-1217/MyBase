package com.kaku.data.mapper

import com.kaku.data.model.RestfulObjectApiResponse
import com.kaku.domain.model.RestfulObjectData

internal object RestfulDataMapper {
    fun toRestfulObjectData(response: RestfulObjectApiResponse): RestfulObjectData =
        with(response) {
            RestfulObjectData(
                id = id.orEmpty(),
                name = name.orEmpty(),
            )
        }
}
