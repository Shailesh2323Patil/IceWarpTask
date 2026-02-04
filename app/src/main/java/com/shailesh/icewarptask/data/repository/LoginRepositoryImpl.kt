package com.shailesh.icewarptask.data.repository

import com.shailesh.icewarptask.data.source.DataSource
import com.shailesh.icewarptask.domain.repository.LoginRepository
import com.shailesh.icewarptask.ui.login.model.User
import io.reactivex.Single

class LoginRepositoryImpl(
    private val remoteDataSource: DataSource.Remote,
    private val localDataSource: DataSource.Local,
    private val cacheDataSource: DataSource.Cache,
) : LoginRepository {
    override fun login(username: String, password: String): Single<User> {
        return remoteDataSource.login(username, password)
            .map { responseDTO ->
                responseDTO.toUser()
            }
            .doOnSuccess { response ->
                localDataSource.insertUser(
                    token = response.token.toString(),
                    host = response.host.toString(),
                    email = response.email.toString()
                )
            }

    }
}