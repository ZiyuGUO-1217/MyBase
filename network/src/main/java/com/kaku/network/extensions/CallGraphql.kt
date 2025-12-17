package com.kaku.network.extensions

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.exception.ApolloException
import okio.IOException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <D : Query.Data> callGraphql(
    call: () -> ApolloCall<D>,
): Result<D> = try {
    val response = call().execute()
    val data = response.data

    when {
        response.hasErrors() -> {
            Result.failure(IOException("GraphQL errors: ${response.errors?.joinToString("; ") { it.message }}"))
        }
        data != null -> {
            Result.success(data)
        }
        else -> {
            Result.failure(IOException("GraphQL call returned no data and no errors"))
        }
    }
} catch (e: ApolloException) {
    Result.failure(e)
} catch (e: Exception) {
    Result.failure(e)
}
