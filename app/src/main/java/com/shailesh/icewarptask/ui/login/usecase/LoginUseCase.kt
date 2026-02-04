package com.shailesh.icewarptask.ui.login.usecase

import com.shailesh.icewarptask.domain.repository.LoginRepository
import com.shailesh.icewarptask.domain.usecase.base.BaseSingleUseCase
import com.shailesh.icewarptask.domain.usecase.mapper.CloudErrorMapper
import com.shailesh.icewarptask.ui.login.model.User
import com.shailesh.icewarptask.util.rxjava.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    val loginRepository: LoginRepository,
    schedulerProvider: SchedulerProvider,
    cloudErrorMapper: CloudErrorMapper
) : BaseSingleUseCase<User>(schedulerProvider, cloudErrorMapper) {
    lateinit var username: String
    lateinit var password: String

    override fun build(): Single<User> {
        return loginRepository.login(username, password)
    }
}