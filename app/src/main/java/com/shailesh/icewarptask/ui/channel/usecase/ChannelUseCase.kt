package com.shailesh.icewarptask.ui.channel.usecase

import com.shailesh.icewarptask.data.remote.dto.ChannelResponseDTO
import com.shailesh.icewarptask.domain.repository.ChannelRepository
import com.shailesh.icewarptask.domain.usecase.base.BaseCoroutineUseCase
import com.shailesh.icewarptask.domain.usecase.mapper.CloudErrorMapper
import com.shailesh.icewarptask.util.rxjava.DispatcherProvider
import javax.inject.Inject

class ChannelUseCase @Inject constructor(
    val channelRepository: ChannelRepository,
    dispatcherProvider: DispatcherProvider,
    cloudErrorMapper: CloudErrorMapper
) : BaseCoroutineUseCase<ChannelResponseDTO>(dispatcherProvider, cloudErrorMapper) {

    lateinit var token: String

    override suspend fun build(): ChannelResponseDTO {
        return channelRepository.getChannels(token)
    }
}