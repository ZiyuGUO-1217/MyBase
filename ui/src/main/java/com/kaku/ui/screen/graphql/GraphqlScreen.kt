package com.kaku.ui.screen.graphql

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.kaku.domain.model.FilmObjectData
import com.kaku.ui.common.UiStates
import com.kaku.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GraphqlScreen(viewModel: GraphqlViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val dispatch = viewModel::dispatch

    ScreenContent(state, dispatch)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ScreenContent(
    state: GraphqlUiState,
    dispatch: (GraphqlUiAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "GraphQL Screen",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors =
                    TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(Modifier.height(16.dp))
                Button(onClick = { dispatch(GraphqlUiAction.LoadData) }) {
                    Text("Load All Films")
                }
                Spacer(Modifier.height(16.dp))
            }

            when (val filmsState = state.films) {
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
                        items = filmsState.data,
                        key = { it.id },
                        contentType = { it::class },
                    ) { film ->
                        FilmCard(film = film)
                    }
                }
            }
        }
    }
}

@Composable
private fun FilmCard(film: FilmObjectData) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = film.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Director: ${film.director}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Release Date: ${film.releaseDate}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun GraphqlScreenPreview(
    @PreviewParameter(GraphqlScreenPreviewParameterProvider::class)
    uiStates: UiStates<List<FilmObjectData>>,
) {
    AppTheme {
        ScreenContent(
            state = GraphqlUiState(films = uiStates),
            dispatch = {},
        )
    }
}

private class GraphqlScreenPreviewParameterProvider : PreviewParameterProvider<UiStates<List<FilmObjectData>>> {
    override val values: Sequence<UiStates<List<FilmObjectData>>>
        get() =
            sequenceOf(
                UiStates.Loading,
                UiStates.Error(),
                UiStates.Success(
                    List(5) {
                        FilmObjectData(
                            id = "film_$it",
                            title = "Star Wars Episode ${it + 1}",
                            director = "Director $it",
                            releaseDate = "2024-0${it + 1}-01",
                        )
                    },
                ),
            )
}
