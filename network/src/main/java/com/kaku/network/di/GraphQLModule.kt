package com.kaku.network.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

private const val BASE_URL = "https://swapi-graphql.netlify.app/graphql/"

@Module
@InstallIn(SingletonComponent::class)
object GraphQLModule {
    @Provides
    @Singleton
    fun provideApolloClient(
        okHttpClient: OkHttpClient,
    ): ApolloClient =
        ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
}
