package com.kaku.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.kaku.test.common.StandardDispatcherRule
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

class MainViewModelTest {
    @get:Rule
    val dispatcherRule = StandardDispatcherRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle()
        viewModel = MainViewModel(savedStateHandle)
    }

    @Test
    fun `initial state is Home`() = runTest {
        viewModel.backStack.test {
            awaitItem() shouldBe listOf(Route.Home)
        }
    }

    @Test
    fun `onNavigateTo adds route`() = runTest {
        viewModel.backStack.test {
            awaitItem() shouldBe listOf(Route.Home)

            viewModel.onNavigateTo(Route.Restful)

            awaitItem() shouldBe listOf(Route.Home, Route.Restful)
        }
    }

    @Test
    fun `onNavigateTo replaces top if size greater than 1`() = runTest {
        viewModel.onNavigateTo(Route.Restful)

        viewModel.backStack.test {
            awaitItem() shouldBe listOf(Route.Home, Route.Restful)

            viewModel.onNavigateTo(Route.Graphql)
            awaitItem() shouldBe listOf(Route.Home, Route.Graphql)
        }
    }

    @Test
    fun `onNavigateTo does nothing if target is same as last`() = runTest {
        viewModel.backStack.test {
            awaitItem() shouldBe listOf(Route.Home)

            viewModel.onNavigateTo(Route.Home)
            expectNoEvents()
        }
    }

    @Test
    fun `onBack removes route`() = runTest {
        // Initial: [Home]
        // Navigate to Restful -> [Home, Restful]
        viewModel.onNavigateTo(Route.Restful)

        viewModel.backStack.test {
            awaitItem() shouldBe listOf(Route.Home, Route.Restful)

            viewModel.onBack()
            awaitItem() shouldBe listOf(Route.Home)
        }
    }

    @Test
    fun `onBack does nothing if only one route`() = runTest {
        viewModel.backStack.test {
            awaitItem() shouldBe listOf(Route.Home)

            viewModel.onBack()
            expectNoEvents()
        }
    }
}
