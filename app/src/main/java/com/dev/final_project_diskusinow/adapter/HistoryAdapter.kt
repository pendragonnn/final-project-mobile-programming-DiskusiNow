package com.dev.final_project_diskusinow.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.final_project_diskusinow.data.network.response.DataHistory
import com.dev.final_project_diskusinow.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<DataHistory, HistoryAdapter.ViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        val room = getItem(position)
        holder.bind(room)
    }

    class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(history: DataHistory) {
            binding.apply {
                tvIdHistory.text = "#${history.id}"
                tvDate.text = history.date
                tvPhoneNumber.text = history.no_phone
                tvStartTime.text = history.time_booking
                tvEndTime.text = addOneHour(history.time_booking)
                tvDescription.text = history.description
                tvParticipant.text = "${history.participant} Orang"
                tvRoom.text = history.room_name
                tvName.text = history.user_name
            }
        }

        private fun addOneHour(time: String): String {
            val (hours, minutes) = time.split(":").map { it.toInt() }
            val newHours = (hours + 1).let {
                if (it < 10) "0$it" else it.toString()
            }
            return "$newHours:${minutes}0"
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataHistory>() {
            override fun areItemsTheSame(oldItem: DataHistory, newItem: DataHistory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataHistory, newItem: DataHistory): Boolean {
                return oldItem == newItem
            }
        }
    }
}