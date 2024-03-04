package com.mumbicodes.data.di

import com.apollographql.apollo3.ApolloClient
import com.mumbicodes.data.repository.AnimeRepositoryImpl
import com.mumbicodes.data.repository.CharacterRepositoryImpl
import com.mumbicodes.data.repository.SearchRepositoryImpl
import com.mumbicodes.data.repository.TrailerRepositoryImpl
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.domain.repository.CharacterRepository
import com.mumbicodes.domain.repository.SearchRepository
import com.mumbicodes.domain.repository.TrailerRepository
import com.mumbicodes.local.preferences.TrailerPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchRepository(apolloClient: ApolloClient): SearchRepository =
        SearchRepositoryImpl(apolloClient = apolloClient)

    @Provides
    @Singleton
    fun provideCharacterRepository(apolloClient: ApolloClient): CharacterRepository =
        CharacterRepositoryImpl(apolloClient = apolloClient)

    @Provides
    @Singleton
    fun provideAnimeRepository(apolloClient: ApolloClient): AnimeRepository =
        AnimeRepositoryImpl(apolloClient = apolloClient)

    @Provides
    @Singleton
    fun provideTrailerRepository(trailerPreferences: TrailerPreferences): TrailerRepository = TrailerRepositoryImpl(trailerPreferences = trailerPreferences)
}