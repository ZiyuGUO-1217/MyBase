package com.kaku.data.data.di

import com.apollographql.apollo.ApolloClient
import com.kaku.data.data.datasource.RemoteGraphQLDataSource
import com.kaku.data.data.datasource.RemoteRestfulDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteRestfulDataSource(
        retrofit: Retrofit,
    ): RemoteRestfulDataSource = retrofit.create()

    @Provides
    @Singleton
    internal fun provideRemoteGraphQLDataSource(
        apolloClient: ApolloClient
    ): RemoteGraphQLDataSource = RemoteGraphQLDataSource(apolloClient)
}
