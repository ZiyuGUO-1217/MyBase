package com.kaku.domain.error

sealed class DomainError(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {
    class Network(cause: Throwable? = null) : DomainError("Network error", cause)

    class Unknown(cause: Throwable? = null) : DomainError("Unknown error", cause)
}
