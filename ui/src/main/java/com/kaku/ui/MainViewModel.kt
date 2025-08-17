package com.kaku.ui

import androidx.lifecycle.ViewModel
import com.kaku.domain.repositories.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor(
    private val myRepository: MyRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _uiEvent: Channel<MainUiEffect> = Channel(BUFFERED)
    val uiEvent: Flow<MainUiEffect> = _uiEvent.receiveAsFlow()

    init {
        _uiState.update { it.copy(info = myRepository.id) }
    }

    fun dispatch(action: MainUiAction) {
        when (action) {

        }
    }
}

data class MainUiState(
    val info: String = "",
)

interface MainUiAction

interface MainUiEffect
