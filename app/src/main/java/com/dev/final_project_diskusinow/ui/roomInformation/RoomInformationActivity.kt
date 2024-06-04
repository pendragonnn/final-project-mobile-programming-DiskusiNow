package com.dev.final_project_diskusinow.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.final_project_diskusinow.adapter.RoomAdapter
import com.dev.final_project_diskusinow.databinding.ActivityRoomInformationBinding
import com.dev.final_project_diskusinow.ui.roomInformation.RoomViewModel
import com.dev.final_project_diskusinow.utils.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RoomInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomInformationBinding
    private val viewModel: RoomViewModel by viewModels() {
        ViewModelFactory.getInstance(this)
    }
    private val adapter = RoomAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        observeRoomData()
    }

    private fun setupViews() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.rvRoom.layoutManager = LinearLayoutManager(this)
        binding.rvRoom.adapter = adapter

        binding.swipeRoom.setOnRefreshListener {
            viewModel.getAllRoom()
        }
    }

    private fun observeRoomData() {
        lifecycleScope.launch{
            viewModel.dataRoom.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.swipeRoom.isRefreshing = true
                    }
                    is Result.Success -> {
                        adapter.submitList(result.data)
                        binding.swipeRoom.isRefreshing = false
                    }
                    is Result.Error -> {
                        result.error?.let { showErrorSnackbar(it) }
                        binding.swipeRoom.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun showErrorSnackbar(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }
}
