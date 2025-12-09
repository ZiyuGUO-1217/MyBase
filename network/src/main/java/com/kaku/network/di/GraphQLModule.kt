package com.kaku.network.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val BASE_URL = "https://swapi-graphql.netlify.app/graphql/"

@Module
@InstallIn(SingletonComponent::class)
object GraphQLModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient =
        ApolloClient
            .Builder()
            .serverUrl(BASE_URL)
            .build()
}
