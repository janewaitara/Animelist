package com.mumbicodes.home

import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.RecommendationsQuery

// TODO create a model class and mapper for the different classes to remove the network from the features
sealed interface RecommendedAnimesUiStates {
    object Loading : RecommendedAnimesUiStates

    data class RecommendedAnimes(
        val recommended: List<RecommendationsQuery.Recommendation> = listOf()
    ) : RecommendedAnimesUiStates

    data class Error(val errorMessage: String) : RecommendedAnimesUiStates
}

sealed interface PopularAnimeStates {
    object Loading : PopularAnimeStates

    data class PopularAnimes(
        val popular: List<AnimeListQuery.Medium> = listOf()
    ) : PopularAnimeStates

    data class Error(val errorMessage: String) : PopularAnimeStates
}