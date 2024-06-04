package com.dev.final_project_diskusinow.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.final_project_diskusinow.adapter.HistoryAdapter
import com.dev.final_project_diskusinow.databinding.ActivityHistoryBookingBinding
import com.dev.final_project_diskusinow.ui.history.HistoryViewModel
import com.dev.final_project_diskusinow.utils.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HistoryBookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBookingBinding
    private val viewModel: HistoryViewModel by viewModels() {
        ViewModelFactory.getInstance(this)
    }
    private val adapter = HistoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }
        setupView()
        observerHistoryData()
    }

    private fun setupView() {
        binding.btnBack.setOnClickListener{
            onBackPressed()
        }
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter

        binding.swipeHistory.setOnRefreshListener {
            viewModel.getHistoryUser()
        }
    }

    private fun observerHistoryData() {
        lifecycleScope.launch {
            viewModel.dataHistory.collect{ result ->
                when (result) {
                    is Result.Loading -> {
                        binding.swipeHistory.isRefreshing = false
                    }
                    is Result.Success -> {
                        adapter.submitList(result.data)
                        binding.swipeHistory.isRefreshing = false
                    }
                    is Result.Error -> {
                        result.error?.let { showErrorSnackbar(it) }
                        binding.swipeHistory.isRefreshing = false
                    }
                }
            }
        }
    }
    private fun showErrorSnackbar(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}