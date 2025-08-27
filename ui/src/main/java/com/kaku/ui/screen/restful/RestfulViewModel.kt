package com.kaku.ui.screen.restful

import androidx.lifecycle.viewModelScope
import com.kaku.domain.model.RestfulObjectData
import com.kaku.domain.repositories.RestfulRepository
import com.kaku.ui.common.MviViewModel
import com.kaku.ui.common.UiAction
import com.kaku.ui.common.UiEffect
import com.kaku.ui.common.UiState
import com.kaku.ui.common.suspendRunCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RestfulViewModel @Inject constructor(
    private val repository: RestfulRepository
) : MviViewModel<RestfulUiState, RestfulUiAction, RestfulUiEffect>() {

    override fun configInitUiState(): RestfulUiState {
        return RestfulUiState()
    }

    override fun dispatch(action: RestfulUiAction) {
        when (action) {
            RestfulUiAction.LoadData -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            suspendRunCatching { repository.getAllItems() }
                .onSuccess { items ->
                    updateUiState { copy(items = items) }
                }
                .onFailure { error ->
                    // Handle error, e.g., send a UiEffect to show an error message
                }
        }
    }
}

data class RestfulUiState(
    val items: List<RestfulObjectData> = emptyList(),
) : UiState

sealed interface RestfulUiAction : UiAction {
    data object LoadData : RestfulUiAction
}

sealed interface RestfulUiEffect : UiEffect
