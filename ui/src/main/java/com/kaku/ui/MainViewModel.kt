package com.kaku.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val BACK_STACK_KEY = "back_stack_key"
    }

    val backStack: StateFlow<List<Route>> =
        savedStateHandle.getStateFlow(BACK_STACK_KEY, listOf(Route.Home))

    fun onBack() {
        updateBackStack { routes ->
            if (routes.size > 1) routes.dropLast(1) else routes
        }
    }

    fun onNavigateTo(target: Route) {
        updateBackStack { routes ->
            when {
                routes.last() == target -> routes
                // singleTop strategy
                routes.size > 1 -> {
                    routes.dropLast(1) + target
                }
                else -> routes + target
            }
        }
    }

    private fun updateBackStack(block: (List<Route>) -> List<Route>) {
        savedStateHandle[BACK_STACK_KEY] = block(backStack.value)
    }
}
