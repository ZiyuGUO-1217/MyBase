package com.kaku.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MviViewModel<S : UiState, A : UiAction, E : UiEffect> : ViewModel() {
    private val _uiState: MutableStateFlow<S> = MutableStateFlow(configInitUiState())
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _uiEffect: Channel<E> = Channel(BUFFERED)
    val uiEffect: Flow<E> = _uiEffect.receiveAsFlow()

    abstract fun configInitUiState(): S

    abstract fun dispatch(action: A)

    protected fun updateUiState(block: S.() -> S) {
        _uiState.update { it.block() }
    }

    protected fun sendUiEffect(effect: E) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }
}

interface UiState

interface UiAction

interface UiEffect
