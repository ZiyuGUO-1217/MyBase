package com.kaku.data.data.mapper

import com.kaku.domain.model.FilmObjectData
import com.kaku.graphql.AllFilmsQuery

internal fun AllFilmsQuery.Data.toData(): List<FilmObjectData> =
    allFilms?.films?.mapNotNull { film ->
        film?.basicFilmInfo?.let { basicFilmInfo ->
            FilmObjectData(
                id = basicFilmInfo.id,
                title = basicFilmInfo.title.orEmpty(),
                director = basicFilmInfo.director.orEmpty(),
                releaseDate = basicFilmInfo.releaseDate.orEmpty(),
            )
        }
    } ?: emptyList()
