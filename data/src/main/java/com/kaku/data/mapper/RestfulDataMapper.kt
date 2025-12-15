package com.kaku.data.mapper

import com.kaku.data.model.RestfulObjectApiResponse
import com.kaku.domain.model.RestfulObjectData

fun RestfulObjectApiResponse.toData(): RestfulObjectData =
    RestfulObjectData(
        id = id.orEmpty(),
        name = name.orEmpty(),
    )
