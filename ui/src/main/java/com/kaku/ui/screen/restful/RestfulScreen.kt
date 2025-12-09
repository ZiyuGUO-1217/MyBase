package com.kaku.ui.screen.restful

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaku.domain.model.RestfulObjectData
import com.kaku.ui.common.UiStates
import com.kaku.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RestfulScreen(viewModel: RestfulViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val dispatch = viewModel::dispatch

    ScreenContent(state, dispatch)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ScreenContent(
    state: RestfulUiState,
    dispatch: (RestfulUiAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Restful Screen",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(Modifier.height(16.dp))
                Button(onClick = { dispatch(RestfulUiAction.LoadData) }) {
                    Text("Load All Items")
                }
                Spacer(Modifier.height(16.dp))
            }

            when (val itemsState = state.items) {
                is UiStates.Loading -> {
                    item {
                        Spacer(modifier = Modifier.height(64.dp))
                        CircularProgressIndicator()
                    }
                }

                is UiStates.Error -> {
                    item {
                        Text("An error occurred")
                    }
                }

                is UiStates.Success -> {
                    items(
                        items = itemsState.data,
                        key = { it.id },
                        contentType = { it::class },
                    ) { item ->
                        Text(text = item.name)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RestfulScreenPreview(
    @PreviewParameter(RestfulScreenPreviewParameterProvider::class)
    uiStates: UiStates<List<RestfulObjectData>>,
) {
    AppTheme {
        ScreenContent(
            state = RestfulUiState(items = uiStates),
            dispatch = {},
        )
    }
}

private class RestfulScreenPreviewParameterProvider : PreviewParameterProvider<UiStates<List<RestfulObjectData>>> {
    override val values: Sequence<UiStates<List<RestfulObjectData>>>
        get() =
            sequenceOf(
                UiStates.Loading,
                UiStates.Error(),
                UiStates.Success(
                    List(10) {
                        RestfulObjectData(id = it.toString(), name = "Item #$it")
                    },
                ),
            )
}
