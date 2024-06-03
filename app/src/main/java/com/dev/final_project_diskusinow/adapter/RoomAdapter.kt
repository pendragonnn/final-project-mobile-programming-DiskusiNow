package com.dev.final_project_diskusinow.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.final_project_diskusinow.data.network.response.DataItem
import com.dev.final_project_diskusinow.databinding.ItemRoomBinding

class RoomAdapter :  ListAdapter<DataItem, RoomAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ItemRoomBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(room: DataItem) {
            binding.apply {
                tvNameRoom.text = room.name
                tvFloorRoom.text = "Lantai ${room.floor}"
                tvCapacityRoom.text = "Kapasitas ${room.capacity} Orang"

            }
        }
    }

    private fun showSlotRoom() {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = getItem(position)
        holder.bind(room)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}