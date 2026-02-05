package com.shailesh.icewarptask.ui.login.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shailesh.icewarptask.R
import com.shailesh.icewarptask.ui.channel.screen.ChannelActivity
import com.shailesh.icewarptask.ui.login.viewmodel.LoginViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(
                R.id.main
            )
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            login(email, password)
        }

        setObservers()
    }

    private fun setObservers() {
        viewModel.liveData.observe(this, { nextActivity() })
    }

    private fun nextActivity() {
        val intent = Intent(this@LoginActivity, ChannelActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password)
    }
}