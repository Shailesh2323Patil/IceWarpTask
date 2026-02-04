package com.shailesh.icewarptask.ui.channel.usecase

import com.shailesh.icewarptask.domain.repository.ChannelRepository
import com.shailesh.icewarptask.ui.channel.model.Channel
import com.shailesh.icewarptask.ui.channel.model.Group
import com.shailesh.icewarptask.ui.login.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    operator fun invoke(): Flow<List<User>> =
        repository.getUsers()
}