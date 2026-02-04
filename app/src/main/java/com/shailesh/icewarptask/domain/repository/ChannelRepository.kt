package com.shailesh.icewarptask.domain.repository

import com.shailesh.icewarptask.data.remote.dto.ChannelResponseDTO
import com.shailesh.icewarptask.ui.channel.model.Channel
import com.shailesh.icewarptask.ui.channel.model.Group
import com.shailesh.icewarptask.ui.login.model.User
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    suspend fun getChannels(
        token: String,
        includeUnreadCount: Boolean = true,
        excludeMembers: Boolean = true,
        includePermissions: Boolean = false
    ): ChannelResponseDTO

    fun getUsers(): Flow<List<User>>
    fun getGroup(): Flow<List<Group>>
    fun getChannelsByGroupFolderName (groupFolderName: String): Flow<List<Channel>>
}