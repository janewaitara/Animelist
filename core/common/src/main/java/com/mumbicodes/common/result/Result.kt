package com.mumbicodes.common.result

import com.apollographql.apollo3.api.Error

sealed interface Result<out T> {
    object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class Failure(val exception: Throwable) : Result<Nothing>
    data class ApplicationError(val errors: List<Error>) : Result<Nothing>
}