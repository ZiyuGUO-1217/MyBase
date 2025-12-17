package com.kaku.ui.screen.restful

import app.cash.turbine.test
import com.kaku.domain.model.RestfulObjectData
import com.kaku.domain.repositories.RestfulRepository
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

class RestfulViewModelTest {
    @get:Rule
    val dispatcherRule = StandardDispatcherRule()

    private val repository: RestfulRepository = mockk()
    private lateinit var viewModel: RestfulViewModel

    @Before
    fun setup() {
        viewModel = RestfulViewModel(repository)
    }

    @Test
    fun `loadData success updates state to Success`() = runTest {
        val items = listOf(
            RestfulObjectData(id = "1", name = "Item 1"),
        )
        coEvery { repository.getAllItems() } returns Result.success(items)

        viewModel.uiState.test {
            awaitItem() // Initial state

            viewModel.dispatch(RestfulUiAction.LoadData)

            awaitItem().items shouldBe UiStates.Loading
            awaitItem().items shouldBe UiStates.Success(items)
        }
    }

    @Test
    fun `loadData failure updates state to Error`() = runTest {
        coEvery { repository.getAllItems() } returns Result.failure(Exception("Network error"))

        viewModel.uiState.test {
            awaitItem() // Initial state

            viewModel.dispatch(RestfulUiAction.LoadData)

            awaitItem().items shouldBe UiStates.Loading
            awaitItem().items.shouldBeInstanceOf<UiStates.Error>()
        }
    }
}
