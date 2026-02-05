package com.shailesh.icewarptask.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shailesh.icewarptask.domain.usecase.model.UseCaseResult
import com.shailesh.icewarptask.ui.login.model.User
import com.shailesh.icewarptask.ui.login.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _livaData = MutableLiveData<User>()
    val liveData = _livaData

    private val _errorData = MutableLiveData<String>()
    val errorData = _errorData

    fun login(email: String, password: String) {
        loginUseCase.username = email
        loginUseCase.password = password

        val disposable = loginUseCase.execute()
            .subscribe { result ->
                when (result) {
                    is UseCaseResult.Success -> {
                        val user = result.data
                        _livaData.value = user
                    }

                    is UseCaseResult.Error -> {
                        _errorData.value = result.error.errorInformation.toString()
                    }
                }
            }

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}