package com.shailesh.icewarptask.domain.usecase.base

import com.shailesh.icewarptask.domain.usecase.mapper.CloudErrorMapper
import com.shailesh.icewarptask.util.rxjava.UseCaseResult
import com.shailesh.icewarptask.util.rxjava.DispatcherProvider
import kotlinx.coroutines.withContext

abstract class BaseCoroutineUseCase<T>(
    private val dispatcherProvider: DispatcherProvider,
    private val cloudErrorMapper: CloudErrorMapper
) {
    protected abstract suspend fun build(): T

    suspend fun execute(): UseCaseResult<T> {
        return try {
            val result = withContext(context = dispatcherProvider.io) {
                build()
            }

            withContext(context = dispatcherProvider.main) {
                UseCaseResult.Success(data = result)
            }
        } catch (throwable: Throwable) {
            UseCaseResult.Error(
                cloudErrorMapper.mapToDomainErrorException(throwable)
            )
        }
    }
}