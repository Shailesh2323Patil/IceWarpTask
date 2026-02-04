package com.shailesh.icewarptask.data.source.cache

import com.shailesh.icewarptask.data.remote.dto.ChannelResponseDTO
import com.shailesh.icewarptask.data.remote.dto.LoginResponseDTO
import com.shailesh.icewarptask.data.source.DataSource
import io.reactivex.Single

class CacheDataSource: DataSource.Cache {
    override fun login(
        username: String,
        password: String
    ): Single<LoginResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(
        token: String,
        includeUnreadCount: Boolean,
        excludeMembers: Boolean,
        includePermissions: Boolean
    ): ChannelResponseDTO {
        TODO("Not yet implemented")
    }
}