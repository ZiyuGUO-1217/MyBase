package com.kaku.data.data.repository

import com.kaku.domain.repositories.MyRepository
import javax.inject.Inject

class DefaultMyRepository @Inject constructor() : MyRepository {
    override val id: String
        get() = "DefaultMyRepository"
}
