package com.dev.final_project_diskusinow.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dev.final_project_diskusinow.R
import com.dev.final_project_diskusinow.data.network.request.LoginRequest
import com.dev.final_project_diskusinow.databinding.ActivityLoginBinding
import com.dev.final_project_diskusinow.ui.login.LoginViewModel
import com.dev.final_project_diskusinow.utils.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels() {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.apply {
            tvClickHere.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            btnShowPassword.setOnClickListener {
                togglePasswordVisibility(edtLoginPassword, btnShowPassword)
            }

            btnLogin.setOnClickListener {
                val email = edtLoginEmail.text.toString()
                val password = edtLoginPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.loginUser(LoginRequest(email, password))
                } else {
                    showErrorSnackbar("Email and password are required")
                }
            }
        }
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        val isPasswordVisible = editText.transformationMethod is PasswordTransformationMethod

        if (isPasswordVisible) {
            imageView.setImageResource(R.drawable.ic_lock_show_password)
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            imageView.setImageResource(R.drawable.ic_lock_hide_password)
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        editText.setSelection(editText.text.length)
    }

    private fun observeViewModel() {
        val intent = Intent(this, MainActivity::class.java)
        lifecycleScope.launch {
            viewModel.loginResult.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        result.data?.token?.let { token ->
                            viewModel.saveUserToken(token)
                            startActivity(intent)
                            finish()
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
                        result.error?.let { showErrorSnackbar(it) }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {

    }

    private fun showErrorSnackbar(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }
}
