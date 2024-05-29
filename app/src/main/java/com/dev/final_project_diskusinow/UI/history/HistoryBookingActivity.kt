package com.dev.final_project_diskusinow.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.final_project_diskusinow.R
import com.dev.final_project_diskusinow.databinding.ActivityHistoryBookingBinding

class HistoryBookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBookingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}