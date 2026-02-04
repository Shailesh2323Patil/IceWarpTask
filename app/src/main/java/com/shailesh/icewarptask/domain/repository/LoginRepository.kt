package com.shailesh.icewarptask.domain.repository

import com.shailesh.icewarptask.ui.login.model.User
import io.reactivex.Single

interface LoginRepository {
    fun login(username: String, password: String): Single<User>
}
