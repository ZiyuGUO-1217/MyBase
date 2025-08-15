package com.kaku.data.data.repository

import com.kaku.domain.repositories.MyRepository

class DefaultMyRepository: MyRepository {
    override val id: String
        get() = "DefaultMyRepository"
}
