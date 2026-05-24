package com.kaku.data.mapper

import com.kaku.domain.model.FilmObjectData
import com.kaku.graphql.AllFilmsQuery

internal object GraphqlDataMapper {
    fun toFilmObjectData(films: AllFilmsQuery.AllFilms?): List<FilmObjectData> =
        films?.films?.mapNotNull { film ->
            film?.basicFilmInfo?.let { basicFilmInfo ->
                FilmObjectData(
                    id = basicFilmInfo.id,
                    title = basicFilmInfo.title.orEmpty(),
                    director = basicFilmInfo.director.orEmpty(),
                    releaseDate = basicFilmInfo.releaseDate.orEmpty(),
                )
            }
        } ?: emptyList()
}
