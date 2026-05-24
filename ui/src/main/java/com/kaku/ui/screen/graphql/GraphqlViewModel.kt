package com.kaku.ui.screen.graphql

import androidx.lifecycle.viewModelScope
import com.kaku.domain.error.DomainError
import com.kaku.domain.model.FilmObjectData
import com.kaku.domain.repositories.GraphqlRepository
import com.kaku.ui.common.MviViewModel
import com.kaku.ui.common.UiAction
import com.kaku.ui.common.UiEffect
import com.kaku.ui.common.UiState
import com.kaku.ui.common.UiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

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
            repository.getAllFilms()
                .onSuccess { films ->
                    updateUiState { copy(films = UiStates.Success(films)) }
                }.onFailure { error ->
                    updateUiState { copy(films = UiStates.Error(error.toUiError())) }
                }
        }
    }

    private fun Throwable.toUiError(): DomainError =
        this as? DomainError ?: DomainError.Unknown(this)
}

data class GraphqlUiState(
    val films: UiStates<List<FilmObjectData>> = UiStates.Success(emptyList()),
) : UiState

sealed interface GraphqlUiAction : UiAction {
    data object LoadData : GraphqlUiAction
}

sealed interface GraphqlUiEffect : UiEffect
