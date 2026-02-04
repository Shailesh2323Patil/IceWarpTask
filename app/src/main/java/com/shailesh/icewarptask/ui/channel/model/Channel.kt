package com.shailesh.icewarptask.ui.channel.model

data class Channel(
    val id: String,
    val name: String?,
    val created: Long?,
    val creator: String?,
    val groupEmail: String?,
    val groupFolderName: String?,
    val threadUnreadCount: Long?
)