package com.kaku.ui

import com.kaku.test.common.StandardDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    val dispatchersRule = StandardDispatcherRule()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
