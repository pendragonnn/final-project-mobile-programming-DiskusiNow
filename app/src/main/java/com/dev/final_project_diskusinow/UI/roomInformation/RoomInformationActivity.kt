package com.dev.final_project_diskusinow.UI

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dev.final_project_diskusinow.UI.roomInformation.RoomViewModel
import com.dev.final_project_diskusinow.databinding.ActivityRoomInformationBinding

class RoomInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomInformationBinding
    private val viewModel: RoomViewModel by viewModels() {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }
        viewModel.getAllRoom()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}