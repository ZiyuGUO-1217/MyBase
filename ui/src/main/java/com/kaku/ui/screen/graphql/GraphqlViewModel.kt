package com.kaku.ui.screen.graphql

import androidx.lifecycle.viewModelScope
import com.kaku.domain.model.FilmObjectData
import com.kaku.domain.repositories.GraphqlRepository
import com.kaku.ui.common.MviViewModel
import com.kaku.ui.common.UiAction
import com.kaku.ui.common.UiEffect
import com.kaku.ui.common.UiState
import com.kaku.ui.common.UiStates
import com.kaku.ui.common.suspendRunCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphqlViewModel @Inject constructor(
    private val repository: GraphqlRepository,
) : MviViewModel<GraphqlUiState, GraphqlUiAction, GraphqlUiEffect>() {
    override fun configInitUiState(): GraphqlUiState = GraphqlUiState()

    override fun dispatch(action: GraphqlUiAction) {
        when (action) {
            GraphqlUiAction.LoadData -> loadData()
        }
    }

    private fun loadData() {
        updateUiState { copy(films = UiStates.Loading) }
        viewModelScope.launch {
            suspendRunCatching { repository.getAllFilms() }
                .onSuccess { films ->
                    updateUiState { copy(films = UiStates.Success(films)) }
                }.onFailure { error ->
                    // Handle error, e.g., send a UiEffect to show an error message
                    updateUiState { copy(films = UiStates.Error()) }
                }
        }
    }
}

data class GraphqlUiState(
    val films: UiStates<List<FilmObjectData>> = UiStates.Success(emptyList()),
) : UiState

sealed interface GraphqlUiAction : UiAction {
    data object LoadData : GraphqlUiAction
}

sealed interface GraphqlUiEffect : UiEffect
