package com.mumbicodes.ui.di

import android.app.Application
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    fun provideJetpackMedia3Player(app: Application): Player = ExoPlayer
        .Builder(app)
        .build()
}