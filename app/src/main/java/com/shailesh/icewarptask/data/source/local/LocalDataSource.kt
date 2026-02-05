package com.shailesh.icewarptask.data.source.local

import android.content.Context
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.google.gson.Gson
import com.shailesh.icewarptask.data.source.DataSource
import com.shailesh.icewraptask.AppDatabase
import com.shailesh.icewraptask.ChannelEntity
import com.shailesh.icewraptask.GroupEntity
import com.shailesh.icewraptask.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalDataSource(context: Context) : DataSource.Local {
    private val driver = AndroidSqliteDriver(
        schema = AppDatabase.Schema,
        context = context.applicationContext,
        name = "app.db"
    )
    private val database = AppDatabase(driver)
    private val userQueries = database.userQueries
    private val chanelQueries = database.channelQueries
    private val groupQueries = database.groupQueries

    override fun insertUser(
        token: String,
        host: String,
        email: String
    ) {
        userQueries.insertUser(
            token = token,
            host = host,
            email = email
        )
    }

    override fun getUser(): Flow<List<UserEntity>> {
        return userQueries.getUsers().asFlow()
            .mapToList(Dispatchers.IO)
    }

    override fun insertChannel(channel: ChannelEntity) {
        chanelQueries.insertChannel(
            id = channel.id,
            name = channel.name,
            created = channel.created?.toLong(),
            creator = channel.creator,
            isMember = channel.isMember,
            groupEmail = channel.groupEmail,
            groupFolderName = channel.groupFolderName,
            isActive = channel.isActive,
            isRecent = channel.isRecent,
            isAutoFollowed = channel.isAutoFollowed,
            isNotifications = channel.isNotifications,
            lastSeen = channel.lastSeen,
            latest = channel.latest?.toLong(),
            unreadCount = channel.unreadCount?.toLong(),
            threadUnreadCount = channel.threadUnreadCount?.toLong(),
            members = Gson().toJson(channel.members)
        )
    }

    override fun insertGroup(groupName: String) {
        groupQueries.insertGroup(name = groupName)
    }

    override fun getGroup(): Flow<List<GroupEntity>> {
        return groupQueries.getAllGroups().asFlow()
            .mapToList(Dispatchers.IO)
    }

    override fun getChannelsByGroupFolderName(groupFolderName: String): Flow<List<ChannelEntity>> {
        return chanelQueries.getChannelByGroupName(
            groupFolderName
        ).asFlow().mapToList(Dispatchers.IO)
    }

    override fun deleteChannels() {
        chanelQueries.deleteChannels()
    }

    override fun deleteGroups() {
        groupQueries.deleteGroups()
    }

    override fun logout(): Flow<Unit> {
        userQueries.deleteUser()
        groupQueries.deleteGroups()
        chanelQueries.deleteChannels()

        return flow { emit(Unit) }
    }
}