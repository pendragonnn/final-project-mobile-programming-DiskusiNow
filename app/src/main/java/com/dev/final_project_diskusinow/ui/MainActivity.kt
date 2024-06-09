package com.dev.final_project_diskusinow.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.dev.final_project_diskusinow.R
import com.dev.final_project_diskusinow.adapter.CustomSpinnerAdapter
import com.dev.final_project_diskusinow.adapter.TimeListAdapter
import com.dev.final_project_diskusinow.data.network.request.BookingRequest
import com.dev.final_project_diskusinow.data.network.response.BookingResponse
import com.dev.final_project_diskusinow.databinding.ActivityMainBinding
import com.dev.final_project_diskusinow.databinding.DialogResultBookingBinding
import com.dev.final_project_diskusinow.utils.DialogDate
import com.dev.final_project_diskusinow.utils.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private val dialogDate by lazy { DialogDate(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupListeners()
        setupNavigationDrawer()
        observeViewModel()
    }

    private fun setupViews() {
        binding.apply {
            cardDate.setOnClickListener {
                dialogDate.setOnDateSelectedListener { selectedDate ->
                    tvDateHint.text = selectedDate
                    val textColor = ContextCompat.getColor(this@MainActivity, R.color.black)
                    tvDateHint.setTextColor(textColor)
                }
                dialogDate.showDatePickerDialog()
            }

            setupSpinner()
        }
    }

    private fun setupListeners() {
        binding.apply {
            edtStartTime.setOnClickListener {
                showTimePickerDialog()
            }

            btnNavigationDrawer.setOnClickListener {
                openDrawer()
            }

            btnRoomInformation.setOnClickListener {
                startNewActivity(RoomInformationActivity::class.java)
            }

            btnFormSubmit.setOnClickListener {
                val noPhone = binding.edtNoContact.text.toString()
                val date = tvDateHint.text.toString()
                val room = mapRoomNameToId(binding.spinnerFormRoom.selectedItem.toString())
                val startTime = binding.edtStartTime.text.toString()
                val participantText = edtTotalParticipant.text.toString()
                val participant = participantText.toIntOrNull() ?: 0
                val description = binding.edtDescription.text.toString()

                viewModel.postBookingRoom(BookingRequest(room ,noPhone, date, participant, startTime, description))
            }
        }
    }

    private fun setupSpinner() {
        val rooms = resources.getStringArray(R.array.form_room_spinner).toList()
        val adapter = CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, rooms)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFormRoom.adapter = adapter
    }

    private fun mapRoomNameToId(roomName: String): Int {
        return when (roomName) {
            "Ruang 1A" -> 1
            "Ruang 1B" -> 2
            "Ruang 2A" -> 3
            "Ruang 2B" -> 4
            else -> 0
        }
    }
    private fun setupNavigationDrawer() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            when (menuItem.itemId) {
                R.id.drawer_history -> startNewActivity(HistoryBookingActivity::class.java)
                R.id.drawer_about_us -> startNewActivity(AboutUsActivity::class.java)
                R.id.drawer_contact_us -> startNewActivity(ContactUsActivity::class.java)
                R.id.drawer_out -> logoutUser()
            }
            menuItem.isCheckable = false
            true
        }
    }

    private fun showTimePickerDialog() {
        val timeList = resources.getStringArray(R.array.time_picker)

        val dialogView = layoutInflater.inflate(R.layout.dialog_time_picker, null)
        val listView = dialogView.findViewById<ListView>(R.id.timeListView)
        val adapter = TimeListAdapter(this, timeList)
        listView.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTime = timeList[position]
            binding.edtStartTime.setText(selectedTime)
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }


    private fun logoutUser() {
        viewModel.logout()
        finish()
    }

    private fun startNewActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
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
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.bookingResult.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        val bookingData = result.data
                        showSnackbar("Booking Ruangan Berhasil")
                        showResultDialog(bookingData)
                        clearFormInputs()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        result.error?.let { showSnackbar(it) }
                    }
                }
            }
        }
    }
    private fun clearFormInputs() {
        binding.edtNoContact.text?.clear()
        binding.tvDateHint.text = ""
        binding.spinnerFormRoom.setSelection(0)
        binding.edtStartTime.text = ""
        binding.edtTotalParticipant.text?.clear()
        binding.edtDescription.text?.clear()
    }
    private fun showLoading(isLoading: Boolean) {

    }

    private fun showSnackbar(Message: String) {
        Snackbar.make(binding.root, Message, Snackbar.LENGTH_LONG).show()
    }
    private fun showResultDialog(bookingResponse: BookingResponse) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_result_booking, null)
        val dialogBinding = DialogResultBookingBinding.bind(dialogView)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogBinding.tvResultId.text = bookingResponse.data?.id.toString()
        dialogBinding.tvResultDate.text = bookingResponse.data?.date
        dialogBinding.tvResultName.text = bookingResponse.data?.user_name
        dialogBinding.tvResultNoPhone.text = bookingResponse.data?.no_phone
        dialogBinding.tvResultStartTime.text = bookingResponse.data?.time_booking
        dialogBinding.tvResultEndTime.text = bookingResponse.data?.time_booking?.let { addOneHour(it) }
        dialogBinding.tvResultRoom.text = bookingResponse.data?.room_name
        dialogBinding.tvResultParticipant.text = bookingResponse.data?.participant.toString()
        dialogBinding.tvResultDescription.text = bookingResponse.data?.description

        dialogBinding.btnCloseResult.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun addOneHour(time: String): String {
        val (hours, minutes) = time.split(":").map { it.toInt() }
        val newHours = (hours + 1).let {
            if (it < 10) "0$it" else it.toString()
        }
        return "$newHours:${minutes}0"
    }
}
