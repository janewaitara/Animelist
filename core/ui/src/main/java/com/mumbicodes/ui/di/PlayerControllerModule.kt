package com.mumbicodes.ui.di

import androidx.media3.common.Player
import com.mumbicodes.ui.controller.PlayerController
import com.mumbicodes.ui.controller.PlayerControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlayerControllerModule {

    @Provides
    fun providesPlayerController(player: Player): PlayerController = PlayerControllerImpl(player)
}