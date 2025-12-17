package com.kaku.data.repository

import com.kaku.data.datasource.RemoteGraphQLDataSource
import com.kaku.graphql.AllFilmsQuery
import com.kaku.graphql.fragment.BasicFilmInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest

class MyGraphqlRepositoryTest {
    private val dataSource: RemoteGraphQLDataSource = mockk()
    private lateinit var repository: MyGraphqlRepository

    @Before
    fun setup() {
        repository = MyGraphqlRepository(dataSource)
    }

    @Test
    fun `getAllFilms returns mapped data`() = runTest {
        val basicFilmInfo = BasicFilmInfo(
            id = "1",
            title = "A New Hope",
            director = "George Lucas",
            releaseDate = "1977-05-25",
        )
        val film = AllFilmsQuery.Film(__typename = "Film", basicFilmInfo = basicFilmInfo)
        val allFilms = AllFilmsQuery.AllFilms(films = listOf(film))
        val data = AllFilmsQuery.Data(allFilms = allFilms)

        coEvery { dataSource.queryAllFilms() } returns Result.success(data)

        repository.getAllFilms()

        coVerify { dataSource.queryAllFilms() }
    }
}
