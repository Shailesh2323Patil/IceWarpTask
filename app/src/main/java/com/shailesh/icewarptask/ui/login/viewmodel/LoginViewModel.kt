package com.shailesh.icewarptask.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import com.shailesh.icewarptask.ui.login.usecase.LoginUseCase
import com.shailesh.icewarptask.util.rxjava.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun login() {
        loginUseCase.username = "testuser@mofa.onice.io"
        loginUseCase.password = "Password123456"

        val disposable = loginUseCase.execute()
            .subscribe { result ->
                when (result) {
                    is UseCaseResult.Success -> {
                        val user = result.data
                    }

                    is UseCaseResult.Error -> {
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