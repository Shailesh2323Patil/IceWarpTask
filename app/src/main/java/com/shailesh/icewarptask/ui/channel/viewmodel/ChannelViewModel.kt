package com.shailesh.icewarptask.ui.channel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shailesh.icewarptask.ui.channel.usecase.ChannelUseCase
import com.shailesh.icewarptask.ui.channel.usecase.GetChannelFromGroupUseCase
import com.shailesh.icewarptask.ui.channel.usecase.GetGroupUseCase
import com.shailesh.icewarptask.ui.channel.usecase.GetUsersUseCase
import com.shailesh.icewarptask.util.rxjava.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val getGroupUseCase: GetGroupUseCase,
    private val getChannelFromGroupUseCase: GetChannelFromGroupUseCase
) : ViewModel() {

    fun getUserDetails() {
        viewModelScope.launch {
            getUsersUseCase()
                .collect { userList ->
                    userList.forEach {
                        channelList(token = it.token.toString())
                    }
                }
        }
    }

    fun channelList(token: String) {
        channelUseCase.token = token

        viewModelScope.launch {
            when (val result = channelUseCase.execute()) {
                is UseCaseResult.Success -> {
                    val channelList = result.data
                }

                is UseCaseResult.Error -> {

                }
            }
        }
    }

    fun getGroupDetails() {
        viewModelScope.launch {
            getGroupUseCase()
                .collect { groupList ->
                    groupList.forEach { group ->
                        group.name
                    }
                }
        }
    }

    fun getChannelFromGroupNameDetails() {
        getChannelFromGroupUseCase.groupFolderName = "Public folders"

        viewModelScope.launch {
            getChannelFromGroupUseCase()
                .collect { channelList ->
                    channelList.forEach { channel ->
                        channel.name
                    }
                }
        }
    }
}