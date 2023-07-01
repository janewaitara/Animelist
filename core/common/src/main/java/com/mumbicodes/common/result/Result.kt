package com.mumbicodes.common.result

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Operation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class Failure(val exception: Throwable) : Result<Nothing>
    data class ApplicationError(val errors: List<Error>) : Result<Nothing>
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T : Operation.Data, R> Flow<ApolloResponse<T>>.asResult(transform: (T) -> R): Flow<Result<R>> =
    this.mapLatest {
        if (it.data != null) {
            Result.Success(transform(it.dataAssertNoErrors))
        } else if (it.hasErrors()) {
            Result.ApplicationError(it.errors!!)
        } else {
            error("Unknown error occurred. There was no data or errors received")
        }
    }.onStart {
        emit(Result.Loading)
    }.catch {
        emit(Result.Failure(it))
    }