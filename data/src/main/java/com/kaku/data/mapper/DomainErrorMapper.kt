package com.kaku.data.mapper

import com.apollographql.apollo.exception.ApolloException
import com.kaku.domain.error.DomainError
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
internal inline fun <T, R> Result<T>.mapCatchingToDomainError(transform: (T) -> R): Result<R> =
    fold(
        onSuccess = { value ->
            try {
                Result.success(transform(value))
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Result.failure(exception.toDomainError())
            }
        },
        onFailure = { throwable ->
            if (throwable is CancellationException) {
                throw throwable
            }
            Result.failure(throwable.toDomainError())
        },
    )

private fun Throwable.toDomainError(): DomainError =
    when (this) {
        is DomainError -> this
        is ApolloException,
        is HttpException,
        is IOException,
        -> DomainError.Network(this)
        else -> DomainError.Unknown(this)
    }
