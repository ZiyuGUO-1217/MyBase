package com.kaku.data.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestfulObjectApiResponse(
    val id: String?,
    val name: String?,
)
