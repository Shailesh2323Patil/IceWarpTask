package com.shailesh.icewarptask.ui.channel.model

data class Group(
    val id: Long,
    val name: String,
    val channelList: MutableList<Channel> = mutableListOf()
)