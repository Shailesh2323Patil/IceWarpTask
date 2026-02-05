package com.shailesh.icewarptask.ui.splash.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shailesh.icewarptask.R
import com.shailesh.icewarptask.ui.channel.screen.ChannelActivity
import com.shailesh.icewarptask.ui.login.screen.LoginActivity
import com.shailesh.icewarptask.ui.splash.viewmodel.SplashViewModel
import com.shailesh.icewarptask.util.constants.AppConstants

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_splash)

        viewModel.navigate.observe(this) { shouldNavigate ->
            if (shouldNavigate == AppConstants.LOGIN) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, ChannelActivity::class.java))
                finish()
            }
        }
    }
}