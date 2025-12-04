package com.kaku.data.data.repository

import com.kaku.data.data.datasource.RemoteGraphQLDataSource
import com.kaku.data.data.mapper.toData
import com.kaku.domain.model.FilmObjectData
import com.kaku.domain.repositories.GraphqlRepository
import javax.inject.Inject

class MyGraphqlRepository @Inject constructor(
    private val dataSource: RemoteGraphQLDataSource,
) : GraphqlRepository {

    override suspend fun getAllFilms(): List<FilmObjectData> {
        return dataSource.queryAllFilms()
            .dataOrThrow()
            .toData()
    }
}
