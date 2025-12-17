package com.kaku.data.repository

import com.kaku.data.datasource.RemoteRestfulDataSource
import com.kaku.data.model.RestfulObjectApiResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
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

        repository.getAllItems()

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

        repository.getItemsById(ids)

        coVerify { remoteDataSource.getObjectsByIds(ids) }
    }

    @Test
    fun `getItem returns mapped data`() = runTest {
        val id = "1"
        val apiResponse = RestfulObjectApiResponse(id = "1", name = "Item 1")
        coEvery { remoteDataSource.getObject(id) } returns apiResponse

        repository.getItem(id)

        coVerify { remoteDataSource.getObject(id) }
    }
}
