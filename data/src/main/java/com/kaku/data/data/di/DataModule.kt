package com.kaku.data.data.di

import com.kaku.data.data.repository.DefaultMyRepository
import com.kaku.data.data.repository.MyGraphqlRepository
import com.kaku.data.data.repository.MyRestfulRepository
import com.kaku.domain.repositories.GraphqlRepository
import com.kaku.domain.repositories.MyRepository
import com.kaku.domain.repositories.RestfulRepository
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
    abstract fun bindMyRepository(repository: DefaultMyRepository): MyRepository

    @Binds
    @Singleton
    abstract fun bindRestfulRepository(repository: MyRestfulRepository): RestfulRepository

    @Binds
    @Singleton
    abstract fun bindGraphqlRepository(repository: MyGraphqlRepository): GraphqlRepository
}
