package com.kaku.data.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.kaku.graphql.AllFilmsQuery
import javax.inject.Inject

class RemoteGraphQLDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
) {
    suspend fun queryAllFilms(): ApolloResponse<AllFilmsQuery.Data> =
        apolloClient
            .query(AllFilmsQuery())
            .execute()
}
