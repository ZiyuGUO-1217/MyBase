package com.kaku.domain.repositories

import com.kaku.domain.model.FilmObjectData

interface GraphqlRepository {
    suspend fun getAllFilms(): Result<List<FilmObjectData>>
}
