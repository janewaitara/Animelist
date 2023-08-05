package com.mumbicodes.home

import com.mumbicodes.model.data.Anime

// TODO create a model class and mapper for the different classes to remove the network from the features
sealed interface RecommendedAnimesUiStates {
    object Loading : RecommendedAnimesUiStates

    data class RecommendedAnimes(
        val recommended: List<Anime> = listOf()
    ) : RecommendedAnimesUiStates

    data class Error(val errorMessage: String) : RecommendedAnimesUiStates
}

sealed interface PopularAnimeStates {
    object Loading : PopularAnimeStates

    data class PopularAnimes(
        val popular: List<Anime> = listOf()
    ) : PopularAnimeStates

    data class Error(val errorMessage: String) : PopularAnimeStates
}

sealed interface TrendingAnimeStates {
    object Loading : TrendingAnimeStates

    data class TrendingAnimes(
        val trending: List<Anime> = listOf()
    ) : TrendingAnimeStates

    data class Error(val errorMessage: String) : TrendingAnimeStates
}