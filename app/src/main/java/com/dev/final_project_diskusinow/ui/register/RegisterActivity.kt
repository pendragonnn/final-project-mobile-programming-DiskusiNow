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
import com.dev.final_project_diskusinow.data.network.request.RegisterRequest
import com.dev.final_project_diskusinow.databinding.ActivityRegisterBinding
import com.dev.final_project_diskusinow.ui.register.RegisterViewModel
import com.dev.final_project_diskusinow.utils.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
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

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.apply {
            lockEdtPassword.setOnClickListener {
                togglePasswordVisibility(edtRegisterPassword, lockEdtPassword)
            }
            lockEdtConfirmPassword.setOnClickListener {
                togglePasswordVisibility(edtRegisterConfirmPassword, lockEdtConfirmPassword)
            }
            btnRegister.setOnClickListener {
                val name = edtRegisterName.text.toString()
                val email = edtRegisterEmail.text.toString()
                val nim = edtRegisterNim.text.toString()
                val password = edtRegisterPassword.text.toString()
                val confirmPassword = edtRegisterConfirmPassword.text.toString()

                if (name.isNotEmpty() && email.isNotEmpty() && nim.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (password == confirmPassword) {
                        viewModel.registerUser(RegisterRequest(name, email, nim, password))
                    } else {
                        showSnackbar("Passwords do not match")
                    }
                } else {
                    showSnackbar("All fields are required")
                }
            }

            tvClickHere.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.registerResult.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        showSnackbar("Registration successful")
                        delay(1500)
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

    private fun showLoading(isLoading: Boolean) {

    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}
