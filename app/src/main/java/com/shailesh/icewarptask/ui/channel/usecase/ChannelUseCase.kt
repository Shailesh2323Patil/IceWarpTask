package com.shailesh.icewarptask.ui.channel.usecase

import com.shailesh.icewarptask.domain.repository.ChannelRepository
import com.shailesh.icewarptask.domain.usecase.base.BaseCoroutineUseCase
import com.shailesh.icewarptask.domain.usecase.mapper.CloudErrorMapper
import com.shailesh.icewarptask.ui.channel.model.Group
import com.shailesh.icewarptask.util.rxjava.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChannelUseCase @Inject constructor(
    val channelRepository: ChannelRepository,
    dispatcherProvider: DispatcherProvider,
    cloudErrorMapper: CloudErrorMapper
) : BaseCoroutineUseCase<Flow<List<Group>>>(dispatcherProvider, cloudErrorMapper) {

    lateinit var token: String

    override suspend fun build(): Flow<List<Group>> {
        return channelRepository.getChannels(token)
    }
}