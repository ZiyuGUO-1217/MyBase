package com.kaku.data.datasource

import com.apollographql.apollo.ApolloClient
import com.kaku.graphql.AllFilmsQuery
import com.kaku.network.extensions.callGraphql
import javax.inject.Inject

class RemoteGraphQLDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
) {
    suspend fun queryAllFilms(): Result<AllFilmsQuery.Data> = callGraphql {
        apolloClient.query(AllFilmsQuery())
    }
}
