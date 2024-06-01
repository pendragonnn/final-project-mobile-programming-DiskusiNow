package com.dev.final_project_diskusinow.UI

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dev.final_project_diskusinow.UI.login.LoginViewModel
import com.dev.final_project_diskusinow.data.network.request.LoginRequest
import com.dev.final_project_diskusinow.databinding.ActivityLoginBinding
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

        binding.tvClickHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.edtLoginEmail.text.toString()
            val password = binding.edtLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUser(LoginRequest(email, password))
            } else {
                showErrorSnackbar("Email and password are required")
            }
        }
        observeViewModel()
    }

    private fun showLoading(isLoading: Boolean) {
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
    private fun showErrorSnackbar(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }
}
