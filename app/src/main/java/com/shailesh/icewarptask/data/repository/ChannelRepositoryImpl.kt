package com.shailesh.icewarptask.data.repository

import com.shailesh.icewarptask.data.remote.dto.ChannelResponseDTO
import com.shailesh.icewarptask.data.source.DataSource
import com.shailesh.icewarptask.domain.repository.ChannelRepository
import com.shailesh.icewarptask.ui.channel.model.Channel
import com.shailesh.icewarptask.ui.channel.model.Group
import com.shailesh.icewarptask.ui.login.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChannelRepositoryImpl(
    private val remoteDataSource: DataSource.Remote,
    private val localDataSource: DataSource.Local,
    private val cacheDataSource: DataSource.Cache,
) : ChannelRepository {
    override suspend fun getChannels(
        token: String,
        includeUnreadCount: Boolean,
        excludeMembers: Boolean,
        includePermissions: Boolean
    ): ChannelResponseDTO {
        val response = remoteDataSource.getChannels(
            token = token,
            includeUnreadCount = includeUnreadCount,
            excludeMembers = excludeMembers,
            includePermissions = includePermissions
        )

        val channels = response.channels.map { it.toEntity() }

        val groupNameSet = mutableSetOf<String>()

        channels.forEach { channel ->
            groupNameSet.add(channel.groupFolderName.toString())
            localDataSource.insertChannel(channel)
        }

        groupNameSet.forEach { groupName ->
            localDataSource.insertGroup(groupName)
        }

        return response
    }

    override fun getUsers(): Flow<List<User>> {
        return localDataSource.getUser().map { entities ->
            entities.map { entity ->
                User(
                    token = entity.token,
                    host = entity.host,
                    email = entity.email
                )
            }
        }
    }

    override fun getGroup(): Flow<List<Group>> {
        return localDataSource.getGroup().map { entities ->
            entities.map { entity ->
                Group(
                    id = entity.id,
                    name = entity.name
                )
            }
        }
    }

    override fun getChannelsByGroupFolderName(groupFolderName: String): Flow<List<Channel>> {
        return localDataSource.getChannelsByGroupFolderName(groupFolderName).map { entities ->
            entities.map { entity ->
                Channel(
                    id = entity.id,
                    name = entity.name,
                    created = entity.created,
                    creator = entity.creator,
                    groupEmail = entity.groupEmail,
                    groupFolderName = entity.groupFolderName,
                    threadUnreadCount = entity.threadUnreadCount
                )
            }
        }
    }
}