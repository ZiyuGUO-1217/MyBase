package com.kaku.data.repository

import com.kaku.data.datasource.RemoteGraphQLDataSource
import com.kaku.data.mapper.GraphqlDataMapper
import com.kaku.domain.model.FilmObjectData
import com.kaku.domain.repositories.GraphqlRepository
import javax.inject.Inject

class MyGraphqlRepository @Inject constructor(
    private val dataSource: RemoteGraphQLDataSource,
) : GraphqlRepository {
    override suspend fun getAllFilms(): List<FilmObjectData> {
        val films = dataSource
            .queryAllFilms()
            .dataOrThrow()
            .allFilms

        return GraphqlDataMapper.toFilmObjectData(films)
    }
}
