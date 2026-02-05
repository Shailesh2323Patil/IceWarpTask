package com.shailesh.icewarptask.ui.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shailesh.icewarptask.ui.channel.usecase.GetUsersUseCase
import com.shailesh.icewarptask.util.constants.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {
    private val _navigate = MutableLiveData<String>()
    val navigate: LiveData<String> = _navigate

    init {
        startSplash()
    }

    private fun startSplash() {
        viewModelScope.launch { getUserDetails() }
    }

    fun getUserDetails() {
        viewModelScope.launch {
            getUsersUseCase().collect { userList ->
                if (userList.isEmpty()) {
                    _navigate.postValue(AppConstants.LOGIN)
                } else {
                    _navigate.postValue(AppConstants.DASHBOARD)
                }
            }
        }
    }
}