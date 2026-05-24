package com.kaku.test.common

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

/**
 * A JUnit Test Rule that sets the Main dispatcher to an unconfined test dispatcher for unit testing.
 *
 * This rule is useful for tests that require immediate execution of coroutines without any delay.
 *
 * Usage:
 * ```
 * @get:Rule
 * val unconfinedDispatcherRule = UnconfinedDispatcherRule()
 * ```
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UnconfinedDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
