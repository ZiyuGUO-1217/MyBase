package com.kaku.ui.screen.restful

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaku.domain.model.RestfulObjectData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RestfulScreen() {
    val viewModel = hiltViewModel<RestfulViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val dispatch = viewModel::dispatch

    ScreenContent( state, dispatch)
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
            TopAppBar(
                title = { Text(text = "Restful Screen") },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.LightGray
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item(key = "button") {
                Spacer(Modifier.height(16.dp))
                Button(onClick = { dispatch(RestfulUiAction.LoadData) }) {
                    Text("Load All Items")
                }
                Spacer(Modifier.height(16.dp))
            }

            items(state.items, key = { it.id }) { item ->
                Text(text = item.name)
            }
        }
    }
}

@Preview
@Composable
private fun RestfulScreenPreview() {
    MaterialTheme {
        ScreenContent(
            state = RestfulUiState(
                items = List(10) {
                    RestfulObjectData(id = it.toString(), name = "Item #$it")
                }
            ),
            dispatch = {}
        )
    }
}
