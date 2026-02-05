package com.shailesh.icewarptask.domain.usecase.model

sealed class UseCaseResult<out T> {
    data class Success<T>(val data: T) : UseCaseResult<T>()
    data class Error(val error: ErrorModel) : UseCaseResult<Nothing>()
}