package com.mumbicodes.domain.di

import android.app.Application
import com.mumbicodes.domain.usecases.FetchYoutubeVideoStreamUrlUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesYoutubeVideoStreamUrlUseCase(
        application: Application
    ): FetchYoutubeVideoStreamUrlUseCase = FetchYoutubeVideoStreamUrlUseCase(application)
}