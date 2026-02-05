package com.shailesh.icewarptask.ui.channel.model

data class GroupUiState(
    val id: Long,
    val name: String,
    val channelList: List<Channel> = emptyList(),
    val isExpanded: Boolean = false,
    val isLoading: Boolean = false
)

data class ScreenUiState(
    val groupList: List<GroupUiState> = emptyList()
)