package com.kaku.ui.common

sealed class UiStates<out T> {
    data object Loading : UiStates<Nothing>()
    data class Success<T>(val data: T) : UiStates<T>()
    data class Error(val e: Exception? = null) : UiStates<Nothing>()
}
