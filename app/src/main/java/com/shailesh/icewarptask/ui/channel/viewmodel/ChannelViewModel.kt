package com.shailesh.icewarptask.ui.channel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shailesh.icewarptask.ui.channel.model.Group
import com.shailesh.icewarptask.ui.channel.model.GroupUiState
import com.shailesh.icewarptask.ui.channel.model.ScreenUiState
import com.shailesh.icewarptask.ui.channel.usecase.ChannelUseCase
import com.shailesh.icewarptask.ui.channel.usecase.GetChannelFromGroupUseCase
import com.shailesh.icewarptask.ui.channel.usecase.GetGroupUseCase
import com.shailesh.icewarptask.ui.channel.usecase.GetUsersUseCase
import com.shailesh.icewarptask.ui.channel.usecase.LogoutUseCase
import com.shailesh.icewarptask.util.rxjava.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val getGroupsUseCase: GetGroupUseCase,
    private val getChannelFromGroupUseCase: GetChannelFromGroupUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScreenUiState())
    val uiState = _uiState.asStateFlow()
    private val _eventsLogout = MutableSharedFlow<Boolean>()
    val eventsLogout: Flow<Boolean> = _eventsLogout

    fun getUserDetails() {
        viewModelScope.launch {
            getUsersUseCase().collect { userList ->
                userList.forEach { user ->
                    channelList(
                        token = user.token.toString()
                    )
                }
            }
        }
    }

    fun getGroupDetails() {
        viewModelScope.launch {
            getGroupsUseCase().collect { groupList ->
                groupList.forEach { group -> group.name }
            }
        }
    }

    fun channelList(token: String) {
        channelUseCase.token = token
        val updatedGroupList = arrayListOf<GroupUiState>()

        viewModelScope.launch {
            when (val result = channelUseCase.execute()) {
                is UseCaseResult.Success -> {
                    val groupData = result.data

                    groupData.collect { groupList ->
                        groupList.forEach { group ->
                            updatedGroupList.add(GroupUiState(group.id, group.name))
                        }
                        _uiState.value = ScreenUiState(groupList = updatedGroupList)
                    }
                }

                is UseCaseResult.Error -> {}
            }
        }
    }

    fun onCategoryClick(group: Group) {
        val current = _uiState.value
        val category = current.groupList.first { it.id == group.id }

        // Toggle collapse
        if (category.isExpanded) {
            updateGroup(group.id) { it.copy(isExpanded = false) }
            return
        }

        // Expand
        updateGroup(group.id) { it.copy(isExpanded = true) }

        // Fetch only if empty
        if (category.channelList.isEmpty()) {
            getChannelFromGroupNameDetails(group = group)
        }
    }

    // Gives you new state to update with existing
    private fun updateGroup(groupId: Long, update: (GroupUiState) -> GroupUiState) {
        _uiState.update { state ->
            state.copy(groupList = state.groupList.map {
                if (it.id == groupId) update(
                    it
                ) else it
            })
        }
    }

    fun getChannelFromGroupNameDetails(group: Group) {
        updateGroup(group.id) { it.copy(isLoading = true) }

        getChannelFromGroupUseCase.groupFolderName = group.name

        viewModelScope.launch {
            getChannelFromGroupUseCase().collect { channelList ->
                updateGroup(
                    group.id
                ) { it.copy(channelList = channelList, isLoading = false) }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase().collect {
                _eventsLogout.emit(true)
            }
        }
    }
}