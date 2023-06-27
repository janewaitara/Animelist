package com.mumbicodes.network.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Provides
    @Singleton
    fun providesApolloClient(): ApolloClient {
        // Creates a 10MB MemoryCacheFactory
        // in-memory normalized cache is for speed
        val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

        return ApolloClient.Builder()
            .serverUrl("https://graphql.anilist.co")
            .normalizedCache(cacheFactory)
            .build()
    }
}