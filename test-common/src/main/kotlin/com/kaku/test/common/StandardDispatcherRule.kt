package com.kaku.test.common

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

/**
 * A JUnit Test Rule that sets the Main dispatcher to a standard test dispatcher for unit testing.
 * This rule is useful for tests that require controlled execution of coroutines.
 *
 * See details:
 * [kotlinx.coroutines.test.advanceUntilIdle]:
 * Runs all other coroutines on the scheduler until there is nothing left in the queue. This is a good default choice
 * to let all pending coroutines run, and it will work in most test scenarios.
 *
 * [kotlinx.coroutines.test.advanceTimeBy]:
 * Advances virtual time by the given amount and runs any coroutines scheduled to run before that point in virtual time.
 *
 * [kotlinx.coroutines.test.runCurrent]:
 * Runs coroutines that are scheduled at the current virtual time.
 *
 * Usage:
 * ```
 * @get:Rule
 * val standardDispatcherRule = StandardDispatcherRule()
 * ```
 */
@OptIn(ExperimentalCoroutinesApi::class)
class StandardDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
