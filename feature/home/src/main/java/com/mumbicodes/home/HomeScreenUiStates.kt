package com.mumbicodes.home

import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.RecommendationsQuery

// TODO create a model class and mapper for the different classes to remove the network from the features
sealed interface HomeScreenUiStates {
    object Loading : HomeScreenUiStates

    data class Animes(
        val recommended: List<RecommendationsQuery.Recommendation>,
        val trending: List<AnimeListQuery.Medium>,
        val popular: List<AnimeListQuery.Medium>
    ) : HomeScreenUiStates
}