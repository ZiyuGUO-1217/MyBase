package com.kaku.data.repository

import com.kaku.domain.repositories.MyRepository
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class DefaultMyRepositoryTest {
    private lateinit var repository: MyRepository

    @Before
    fun setUp() {
        repository = DefaultMyRepository()
    }

    @Test
    fun `id returns correct value`() {
        repository.id shouldBe "DefaultMyRepository"
    }
}
