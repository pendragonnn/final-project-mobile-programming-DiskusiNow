package com.dev.final_project_diskusinow.utils

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

class DialogDate(private val context: Context) {

    private var onDateSelectedListener: ((String) -> Unit)? = null

    fun setOnDateSelectedListener(listener: (String) -> Unit) {
        this.onDateSelectedListener = listener
    }

    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                onDateSelectedListener?.invoke(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        datePickerDialog.show()
    }
}
