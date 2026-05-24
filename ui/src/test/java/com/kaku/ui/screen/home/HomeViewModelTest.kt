package com.kaku.ui.screen.home

import app.cash.turbine.test
import com.kaku.domain.repositories.MyRepository
import com.kaku.test.common.StandardDispatcherRule
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

class HomeViewModelTest {
    @get:Rule
    val dispatcherRule = StandardDispatcherRule()

    private val repository: MyRepository = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        every { repository.id } returns "Test ID"
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `GetInfo updates info and sends ShowToast effect`() = runTest {
        viewModel.uiState.test {
            awaitItem() // Initial state

            viewModel.dispatch(HomeUiAction.GetInfo)

            awaitItem().info shouldBe "Test ID"
        }
    }

    @Test
    fun `GetInfo sends ShowToast effect`() = runTest {
        viewModel.uiEffect.test {
            viewModel.dispatch(HomeUiAction.GetInfo)
            awaitItem() shouldBe HomeUiEffect.ShowToast
        }
    }
}
