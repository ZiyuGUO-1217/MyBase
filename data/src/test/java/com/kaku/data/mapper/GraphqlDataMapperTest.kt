package com.kaku.data.mapper

import com.kaku.graphql.AllFilmsQuery
import com.kaku.graphql.fragment.BasicFilmInfo
import io.kotest.matchers.shouldBe
import org.junit.Test

class GraphqlDataMapperTest {
    @Test
    fun `toData maps AllFilmsQuery Data to List of FilmObjectData correctly`() {
        val basicFilmInfo = BasicFilmInfo(
            id = "1",
            title = "A New Hope",
            director = "George Lucas",
            releaseDate = "1977-05-25",
        )
        val film = AllFilmsQuery.Film(__typename = "Film", basicFilmInfo = basicFilmInfo)
        val allFilms = AllFilmsQuery.AllFilms(films = listOf(film))

        val result = GraphqlDataMapper.toFilmObjectData(allFilms)

        result.size shouldBe 1
        result[0].id shouldBe "1"
        result[0].title shouldBe "A New Hope"
        result[0].director shouldBe "George Lucas"
        result[0].releaseDate shouldBe "1977-05-25"
    }

    @Test
    fun `toData handles null allFilms correctly`() {
        val result = GraphqlDataMapper.toFilmObjectData(null)

        result shouldBe emptyList()
    }

    @Test
    fun `toData handles null films list correctly`() {
        val allFilms = AllFilmsQuery.AllFilms(films = null)

        val result = GraphqlDataMapper.toFilmObjectData(allFilms)

        result shouldBe emptyList()
    }

    @Test
    fun `toData filters out null films`() {
        val allFilms = AllFilmsQuery.AllFilms(films = listOf(null))
        val data = AllFilmsQuery.Data(allFilms = allFilms)

        val result = GraphqlDataMapper.toFilmObjectData(data.allFilms)

        result shouldBe emptyList()
    }
}
