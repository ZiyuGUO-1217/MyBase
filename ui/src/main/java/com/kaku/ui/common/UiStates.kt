package com.kaku.ui.common

import com.kaku.domain.error.DomainError

sealed class UiStates<out T> {
    data object Loading : UiStates<Nothing>()

    data class Success<T>(
        val data: T,
    ) : UiStates<T>()

    data class Error(
        val error: DomainError? = null,
    ) : UiStates<Nothing>()
}
