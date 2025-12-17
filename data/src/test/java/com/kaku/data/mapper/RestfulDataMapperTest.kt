package com.kaku.data.mapper

import com.kaku.data.model.RestfulObjectApiResponse
import io.kotest.matchers.shouldBe
import org.junit.Test

class RestfulDataMapperTest {
    @Test
    fun `toData maps RestfulObjectApiResponse to RestfulObjectData correctly`() {
        val response = RestfulObjectApiResponse(
            id = "1",
            name = "Test Name",
        )

        val result = RestfulDataMapper.toRestfulObjectData(response)

        result.id shouldBe "1"
        result.name shouldBe "Test Name"
    }

    @Test
    fun `toData handles null values correctly`() {
        val response = RestfulObjectApiResponse(
            id = null,
            name = null,
        )

        val result = RestfulDataMapper.toRestfulObjectData(response)

        result.id shouldBe ""
        result.name shouldBe ""
    }
}
