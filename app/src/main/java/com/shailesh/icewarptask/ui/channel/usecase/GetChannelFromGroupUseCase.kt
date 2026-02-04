package com.shailesh.icewarptask.ui.channel.usecase

import com.shailesh.icewarptask.domain.repository.ChannelRepository
import com.shailesh.icewarptask.ui.channel.model.Channel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChannelFromGroupUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    lateinit var groupFolderName: String

    operator fun invoke(): Flow<List<Channel>> =
        repository.getChannelsByGroupFolderName(groupFolderName)
}