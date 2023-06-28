package com.mumbicodes.common.result

import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException

sealed interface Result<out T> {
    object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class ProtocolError(val exception: ApolloException) : Result<Nothing>
    data class ApplicationError(val errors: List<Error>) : Result<Nothing>
}