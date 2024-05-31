package com.dev.final_project_diskusinow.UI

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dev.final_project_diskusinow.R
import com.dev.final_project_diskusinow.UI.login.LoginViewModel
import com.dev.final_project_diskusinow.UI.register.RegisterViewModel
import com.dev.final_project_diskusinow.data.network.request.RegisterRequest
import com.dev.final_project_diskusinow.databinding.ActivityRegisterBinding
import com.dev.final_project_diskusinow.utils.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels() {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.edtRegisterName.text.toString()
            val email = binding.edtRegisterEmail.text.toString()
            val nim = binding.edtRegisterNim.text.toString()
            val password = binding.edtRegisterPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || nim.isEmpty() || password.isEmpty()) {
                showSnackbar("All fields are required")
            } else {
                viewModel.registerUser(RegisterRequest(name, email, nim, password))
            }
        }

        binding.tvClickHere.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.registerResult.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        showSnackbar("Registration successful")
                        finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        result.error?.let { showSnackbar(it) }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}
