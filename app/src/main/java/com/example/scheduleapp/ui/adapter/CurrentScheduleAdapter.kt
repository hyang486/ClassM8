package com.example.scheduleapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.databinding.ItemCurrentScheduleBinding

class CurrentScheduleAdapter(
    private val onItemClick: (Event) -> Unit
) : ListAdapter<Event, CurrentScheduleViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentScheduleViewHolder {
        val binding =
            ItemCurrentScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentScheduleViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CurrentScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
                oldItem.name == newItem.name
        }
    }
}

class CurrentScheduleViewHolder(
    private val binding: ItemCurrentScheduleBinding,
    private val onItemClick: (Event) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Event) {
        binding.item = item
        itemView.setOnClickListener {
            onItemClick(item)
        }
    }

}