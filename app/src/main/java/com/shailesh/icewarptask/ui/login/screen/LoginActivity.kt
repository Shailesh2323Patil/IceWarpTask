package com.shailesh.icewarptask.ui.login.screen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.shailesh.icewarptask.R
import com.shailesh.icewarptask.ui.channel.screen.ChannelActivity
import com.shailesh.icewarptask.ui.login.viewmodel.LoginViewModel
import com.shailesh.icewarptask.util.constants.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private var tilEmail : TextInputLayout? = null
    private var etEmail : EditText? = null
    private var tilPassword : TextInputLayout? = null
    private var etPassword : EditText? = null
    private var btnSignIn : Button? = null
    private var progressBar : ProgressBar? = null

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

        btnSignIn = findViewById(R.id.btnSignIn)
        etEmail = findViewById(R.id.etEmail)
        tilEmail = findViewById(R.id.tilEmail)
        etPassword = findViewById(R.id.etPassword)
        tilPassword = findViewById(R.id.tilPassword)
        progressBar = findViewById(R.id.progressBar)

        updateLoginButtonState()
        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners() {
        btnSignIn?.setOnClickListener {
            val isEmailValid = validateEmail()
            val isPasswordValid = validatePassword()

            if (isEmailValid && isPasswordValid) {
                val email = etEmail?.text.toString()
                val password = etPassword?.text.toString()

                showLoader(true)

                login(email, password)
            }
        }

        etEmail?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateLoginButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etPassword?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateLoginButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setObservers() {
        viewModel.liveData.observe(this, {
            showLoader(false)
            nextActivity()
        })

        viewModel.errorData.observe(this, { message ->
            showLoader(false)
            Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show()
        })
    }

    private fun nextActivity() {
        val intent = Intent(this@LoginActivity, ChannelActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password)
    }

    private fun validateEmail(): Boolean {
        val email = etEmail?.text.toString().trim()

        if (email.isEmpty()) {
            tilEmail?.error = getString(R.string.email_is_required)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail?.error = getString(R.string.enter_a_valid_email_address)
            return false
        } else {
            tilEmail?.error = null
            return true
        }
    }

    private fun validatePassword(): Boolean {
        val password = etPassword?.text.toString()

        return when {
            password.isEmpty() -> {
                tilPassword?.error = getString(R.string.password_is_required)
                false
            }
            password.length < AppConstants.PASSWORD_LENGTH -> {
                tilPassword?.error = getString(R.string.password_length_error)
                false
            }
            else -> {
                tilPassword?.error = null
                true
            }
        }
    }

    private fun updateLoginButtonState() {
        btnSignIn?.isEnabled = validateEmail() && validatePassword()
    }

    private fun showLoader(show: Boolean) {
        progressBar?.visibility = if (show) View.VISIBLE else View.GONE
    }
}