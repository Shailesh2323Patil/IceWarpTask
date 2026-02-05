package com.shailesh.icewarptask.domain.usecase.base

import com.shailesh.icewarptask.domain.usecase.mapper.CloudErrorMapper
import com.shailesh.icewarptask.domain.usecase.model.UseCaseResult
import com.shailesh.icewarptask.util.rxjava.SchedulerProvider
import io.reactivex.Single

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