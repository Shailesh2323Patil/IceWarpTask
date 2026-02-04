package com.shailesh.icewarptask.data.remote.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.shailesh.icewraptask.ChannelEntity

data class ChannelResponseDTO(
    @SerializedName("channels") var channels: ArrayList<ChannelsDTO> = arrayListOf(),
    @SerializedName("ok") var ok: Boolean? = null
) {
    data class ChannelsDTO(
        @SerializedName("id") var id: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("created") var created: Int? = null,
        @SerializedName("creator") var creator: String? = null,
        @SerializedName("is_member") var isMember: Boolean? = null,
        @SerializedName("group_email") var groupEmail: String? = null,
        @SerializedName("group_folder_name") var groupFolderName: String? = null,
        @SerializedName("is_active") var isActive: Boolean? = null,
        @SerializedName("is_recent") var isRecent: Boolean? = null,
        @SerializedName("is_auto_followed") var isAutoFollowed: Boolean? = null,
        @SerializedName("is_notifications") var isNotifications: Boolean? = null,
        @SerializedName("last_seen") var lastSeen: String? = null,
        @SerializedName("latest") var latest: Int? = null,
        @SerializedName("unread_count") var unreadCount: Int? = null,
        @SerializedName("thread_unread_count") var threadUnreadCount: Int? = null,
        @SerializedName("members") var members: ArrayList<String> = arrayListOf()
    ) {
        fun toEntity(): ChannelEntity =
            ChannelEntity(
                id = id.toString(),
                name = name,
                created = created?.toLong(),
                creator = creator,
                isMember = if (isMember == true) 1 else 0,
                groupEmail = groupEmail,
                groupFolderName = groupFolderName,
                isActive = if (isActive == true) 1 else 0,
                isRecent = if (isRecent == true) 1 else 0,
                isAutoFollowed = if (isAutoFollowed == true) 1 else 0,
                isNotifications = if (isNotifications == true) 1 else 0,
                lastSeen = lastSeen,
                latest = latest?.toLong(),
                unreadCount = unreadCount?.toLong(),
                threadUnreadCount = threadUnreadCount?.toLong(),
                members = Gson().toJson(members)
            )
    }
}