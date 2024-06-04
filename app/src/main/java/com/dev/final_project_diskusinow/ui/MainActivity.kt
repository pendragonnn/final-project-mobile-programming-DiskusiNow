package com.dev.final_project_diskusinow.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import com.dev.final_project_diskusinow.R
import com.dev.final_project_diskusinow.ui.resultPage.ResultPageActivity
import com.dev.final_project_diskusinow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavigationDrawer.setOnClickListener {
            openDrawer()
        }
        binding.btnRoomInformation.setOnClickListener{
            val intent = Intent(this,RoomInformationActivity::class.java)
            startActivity(intent)
        }
        binding.btnFormSubmit.setOnClickListener {
            val intent = Intent(this, ResultPageActivity::class.java)
            startActivity(intent)
        }
        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawer(GravityCompat.START)

            when (menuItem.itemId) {
                R.id.drawer_history -> {
                    val intent = Intent(this, HistoryBookingActivity::class.java)
                    startActivity(intent)
                    menuItem.isCheckable = false
                    true
                }
                R.id.drawer_about_us -> {
                    val intent = Intent(this, AboutUsActivity::class.java)
                    startActivity(intent)
                    menuItem.isCheckable = false
                    true
                }
                R.id.drawer_contact_us -> {
                    val intent = Intent(this, ContactUsActivity::class.java)
                    startActivity(intent)
                    menuItem.isCheckable = false
                    true
                }
                R.id.drawer_out -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
