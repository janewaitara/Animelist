package com.mumbicodes.home

import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.RecommendationsQuery

// TODO create a model class and mapper for the different classes to remove the network from the features
sealed interface HomeScreenUiStates {
    object Loading : HomeScreenUiStates

    data class Animes(
        val recommended: List<RecommendationsQuery.Recommendation> = listOf(),
        val trending: List<AnimeListQuery.Medium> = listOf(),
        val popular: List<AnimeListQuery.Medium> = listOf()
    ) : HomeScreenUiStates

    data class Error(val errorMessage: String) : HomeScreenUiStates
}

sealed interface PopularAnimeStates {
    object Loading : PopularAnimeStates

    data class PopularAnimes(
        val popular: List<AnimeListQuery.Medium> = listOf()
    ) : PopularAnimeStates

    data class Error(val errorMessage: String) : PopularAnimeStates
}