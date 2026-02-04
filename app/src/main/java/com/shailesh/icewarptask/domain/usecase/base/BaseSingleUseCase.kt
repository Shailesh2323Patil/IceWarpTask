package com.shailesh.icewarptask.domain.usecase.base

import com.shailesh.icewarptask.domain.usecase.mapper.CloudErrorMapper
import com.shailesh.icewarptask.util.rxjava.UseCaseResult
import com.shailesh.icewarptask.util.rxjava.DispatcherProvider
import com.shailesh.icewarptask.util.rxjava.SchedulerProvider
import io.reactivex.Single
import kotlinx.coroutines.withContext

abstract class BaseSingleUseCase<T>(
    private val schedulerProvider: SchedulerProvider,
    private val cloudErrorMapper: CloudErrorMapper
) {
    protected abstract fun build(): Single<T>

    fun execute(): Single<UseCaseResult<T>> {
        return build()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .map<UseCaseResult<T>> { UseCaseResult.Success(it) }
            .onErrorReturn {
                UseCaseResult.Error(cloudErrorMapper.mapToDomainErrorException(it))
            }
    }
}