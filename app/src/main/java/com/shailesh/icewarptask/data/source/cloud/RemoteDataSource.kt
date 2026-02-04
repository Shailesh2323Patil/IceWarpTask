package com.shailesh.icewarptask.data.source.cloud

import com.shailesh.icewarptask.data.remote.IceWarpApiService
import com.shailesh.icewarptask.data.remote.dto.ChannelResponseDTO
import com.shailesh.icewarptask.data.remote.dto.LoginResponseDTO
import com.shailesh.icewarptask.data.source.DataSource
import io.reactivex.Single

class RemoteDataSource(private val api: IceWarpApiService) : DataSource.Remote {
    override fun login(username: String, password: String): Single<LoginResponseDTO> {
        return api.login(username, password)
    }

    override suspend fun getChannels(
        token: String,
        includeUnreadCount: Boolean,
        excludeMembers: Boolean,
        includePermissions: Boolean
    ): ChannelResponseDTO {
        return api.getChannels(
            token = token,
            includeUnreadCount = includeUnreadCount,
            excludeMembers = excludeMembers,
            includePermissions = includePermissions
        )
    }
}