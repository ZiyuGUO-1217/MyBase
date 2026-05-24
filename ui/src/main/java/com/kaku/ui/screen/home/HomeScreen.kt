package com.kaku.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.kaku.ui.screen.home.component.Greeting

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 32.dp),
            )
        },
    ) { innerPadding ->
        ScreenContent(
            state = state,
            modifier = Modifier.padding(innerPadding),
            dispatch = viewModel::dispatch,
        )
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) {
        viewModel.uiEffect
            .flowWithLifecycle(lifecycle)
            .collect { handleUiEffects(it, snackbarHostState) }
    }
}

private suspend fun handleUiEffects(
    effect: HomeUiEffect,
    snackbarHostState: SnackbarHostState,
) {
    when (effect) {
        HomeUiEffect.ShowToast -> {
            snackbarHostState.showSnackbar(message = "GetInfo succeed!")
        }
    }
}

@Composable
private fun ScreenContent(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    dispatch: (HomeUiAction) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Greeting(
            name = state.info,
            modifier = Modifier.align(Alignment.Center),
        )
        Button(
            onClick = { dispatch(HomeUiAction.GetInfo) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
        ) {
            Text("Get Info")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenContentPreview() {
    ScreenContent(
        state = HomeUiState(info = "Preview Content"),
        dispatch = {},
    )
}
