package com.kaku.data.data.di

import com.kaku.data.data.repository.DefaultMyRepository
import com.kaku.domain.repositories.MyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMyRepository(
        repository: DefaultMyRepository,
    ): MyRepository
}
