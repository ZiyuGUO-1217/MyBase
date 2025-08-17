package com.kaku.ui.screen.home

import com.kaku.domain.repositories.MyRepository
import com.kaku.ui.common.MviViewModel
import com.kaku.ui.common.UiAction
import com.kaku.ui.common.UiEffect
import com.kaku.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val myRepository: MyRepository,
) : MviViewModel<HomeUiState, HomeUiAction, HomeUiEffect>() {

    override fun configInitUiState(): HomeUiState {
        return HomeUiState(
            info = "Android"
        )
    }

    override fun dispatch(action: HomeUiAction) {
        when (action) {
            HomeUiAction.GetInfo -> getRepoInfo()
        }
    }

    private fun getRepoInfo() {
        updateUiState {
            copy(info = myRepository.id)
        }
        sendUiEffect(HomeUiEffect.ShowToast)
    }
}

data class HomeUiState(
    val info: String = "",
) : UiState

sealed interface HomeUiAction : UiAction {
    data object GetInfo : HomeUiAction
}

sealed interface HomeUiEffect : UiEffect {
    data object ShowToast : HomeUiEffect
}
