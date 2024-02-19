package com.mumbicodes.home.di

import android.app.Application
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PlayerModule {

    @Provides
    fun provideJetpackMedia3Player(app: Application): Player = ExoPlayer
        .Builder(app)
        .build()
}