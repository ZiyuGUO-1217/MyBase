package com.kaku.data.repository

import com.kaku.data.datasource.RemoteRestfulDataSource
import com.kaku.data.model.RestfulObjectApiResponse
import com.kaku.domain.error.DomainError
import com.kaku.domain.model.RestfulObjectData
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlinx.coroutines.test.runTest

class MyRestfulRepositoryTest {
    private val remoteDataSource: RemoteRestfulDataSource = mockk()
    private lateinit var repository: MyRestfulRepository

    @Before
    fun setup() {
        repository = MyRestfulRepository(remoteDataSource)
    }

    @Test
    fun `getAllItems returns mapped data`() = runTest {
        val apiResponse = listOf(
            RestfulObjectApiResponse(id = "1", name = "Item 1"),
            RestfulObjectApiResponse(id = "2", name = "Item 2"),
        )
        coEvery { remoteDataSource.getAllObjects() } returns apiResponse

        val result = repository.getAllItems()

        result.getOrNull() shouldBe listOf(
            RestfulObjectData(id = "1", name = "Item 1"),
            RestfulObjectData(id = "2", name = "Item 2"),
        )
        coVerify { remoteDataSource.getAllObjects() }
    }

    @Test
    fun `getItemsById returns mapped data`() = runTest {
        val ids = listOf("1", "2")
        val apiResponse = listOf(
            RestfulObjectApiResponse(id = "1", name = "Item 1"),
            RestfulObjectApiResponse(id = "2", name = "Item 2"),
        )
        coEvery { remoteDataSource.getObjectsByIds(ids) } returns apiResponse

        val result = repository.getItemsById(ids)

        result.getOrNull() shouldBe listOf(
            RestfulObjectData(id = "1", name = "Item 1"),
            RestfulObjectData(id = "2", name = "Item 2"),
        )
        coVerify { remoteDataSource.getObjectsByIds(ids) }
    }

    @Test
    fun `getItem returns mapped data`() = runTest {
        val id = "1"
        val apiResponse = RestfulObjectApiResponse(id = "1", name = "Item 1")
        coEvery { remoteDataSource.getObject(id) } returns apiResponse

        val result = repository.getItem(id)

        result.getOrNull() shouldBe RestfulObjectData(id = "1", name = "Item 1")
        coVerify { remoteDataSource.getObject(id) }
    }

    @Test
    fun `getAllItems maps network failure to domain error`() = runTest {
        coEvery { remoteDataSource.getAllObjects() } throws IOException("Network error")

        val result = repository.getAllItems()

        result.exceptionOrNull().shouldBeInstanceOf<DomainError.Network>()
    }
}
