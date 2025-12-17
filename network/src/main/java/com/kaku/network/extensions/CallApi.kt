package com.kaku.network.extensions

import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <T> callApi(block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (exception: Exception) {
        Result.failure(exception)
    }
