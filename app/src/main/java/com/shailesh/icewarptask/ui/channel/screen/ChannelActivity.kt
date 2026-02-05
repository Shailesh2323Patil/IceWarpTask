package com.shailesh.icewarptask.ui.channel.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shailesh.icewarptask.ui.channel.viewmodel.ChannelViewModel
import com.shailesh.icewarptask.ui.login.screen.LoginActivity
import com.shailesh.icewarptask.ui.theme.IceWrapTaskTheme

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChannelActivity : ComponentActivity() {
    private val viewModel: ChannelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            IceWrapTaskTheme(darkTheme = isDarkTheme) {
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    viewModel.errorEvent.collect { message ->
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                }

                LaunchedEffect(
                    Unit
                ) {
                    channelList()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var showLogoutDialog by remember { mutableStateOf(false) }

                    var showExitDialog by remember { mutableStateOf(false) }

                    Box(
                        modifier =
                            Modifier.padding(innerPadding)
                    ) {
                        ChannelsScreen(
                            viewModel = viewModel,
                            onBackClick = { showExitDialog = true },
                            onLogoutClick = { showLogoutDialog = true },
                            onThemeChangeClick = { isDarkTheme = !isDarkTheme },
                            isDarkTheme = isDarkTheme
                        )

                        if (showLogoutDialog) {
                            LogoutDialog(
                                onDismiss = { showLogoutDialog = false },
                                onLogout = {
                                    showLogoutDialog = false
                                    viewModel.logout()
                                })
                        }

                        if (showExitDialog) {
                            ExitDialog(
                                onDismiss = { showExitDialog = false },
                                onExit = {
                                    showExitDialog = false
                                    finish()
                                }
                            )
                        }
                    }
                }
            }
        }

        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsLogout.collect {
                    event -> nextActivity()
                }
            }
        }
    }

    private fun channelList() {
        viewModel.getUserDetails()
    }

    private fun nextActivity() {
        val intent = Intent(this@ChannelActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}