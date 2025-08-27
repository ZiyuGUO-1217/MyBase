package com.kaku.data.data.mapper

import com.kaku.data.data.model.RestfulObjectApiResponse
import com.kaku.domain.model.RestfulObjectData

fun RestfulObjectApiResponse.toData(): RestfulObjectData {
    return RestfulObjectData(
        id = id.orEmpty(),
        name = name.orEmpty()
    )
}
