package com.shailesh.icewarptask.data.source

import com.shailesh.icewarptask.data.remote.dto.ChannelResponseDTO
import com.shailesh.icewarptask.data.remote.dto.LoginResponseDTO
import com.shailesh.icewraptask.ChannelEntity
import com.shailesh.icewraptask.GroupEntity
import com.shailesh.icewraptask.UserEntity
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

class DataSource {
    interface Remote {
        fun login(username: String, password: String): Single<LoginResponseDTO>

        suspend fun getChannels(
            token: String,
            includeUnreadCount: Boolean = true,
            excludeMembers: Boolean = true,
            includePermissions: Boolean = false
        ): ChannelResponseDTO
    }

    interface Local {
        fun insertUser(token: String, host: String, email: String)
        fun getUser(): Flow<List<UserEntity>>
        fun insertChannel(channel: ChannelEntity)
        fun insertGroup(groupName: String)
        fun getGroup(): Flow<List<GroupEntity>>
        fun getChannelsByGroupFolderName(
            groupFolderName: String
        ): Flow<List<ChannelEntity>>
    }

    interface Cache : Remote
}