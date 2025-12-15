package com.kaku.ui.screen.graphql

import app.cash.turbine.test
import com.kaku.domain.model.FilmObjectData
import com.kaku.domain.repositories.GraphqlRepository
import com.kaku.test.common.StandardDispatcherRule
import com.kaku.ui.common.UiStates
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

class GraphqlViewModelTest {
    @get:Rule
    val dispatcherRule = StandardDispatcherRule()

    private val repository: GraphqlRepository = mockk()
    private lateinit var viewModel: GraphqlViewModel

    @Before
    fun setup() {
        viewModel = GraphqlViewModel(repository)
    }

    @Test
    fun `loadData success updates state to Success`() = runTest {
        val films = listOf(
            FilmObjectData(
                id = "1",
                title = "A New Hope",
                director = "George Lucas",
                releaseDate = "1977-05-25",
            ),
        )
        coEvery { repository.getAllFilms() } returns films

        viewModel.uiState.test {
            awaitItem() // Initial state

            viewModel.dispatch(GraphqlUiAction.LoadData)

            awaitItem().films shouldBe UiStates.Loading
            awaitItem().films shouldBe UiStates.Success(films)
        }
    }

    @Test
    fun `loadData failure updates state to Error`() = runTest {
        coEvery { repository.getAllFilms() } throws Exception("Network error")

        viewModel.uiState.test {
            awaitItem() // Initial state

            viewModel.dispatch(GraphqlUiAction.LoadData)

            awaitItem().films shouldBe UiStates.Loading
            awaitItem().films.shouldBeInstanceOf<UiStates.Error>()
        }
    }
}
