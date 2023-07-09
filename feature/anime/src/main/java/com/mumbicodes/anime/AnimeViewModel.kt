package com.mumbicodes.anime

import androidx.lifecycle.ViewModel
import com.mumbicodes.domain.repository.AnimeRepository
import javax.inject.Inject

class AnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel()