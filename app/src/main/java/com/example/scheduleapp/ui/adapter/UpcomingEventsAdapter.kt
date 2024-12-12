package com.example.scheduleapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.databinding.ItemUpcommingEventBinding

class UpcomingEventsAdapter(
        private val onItemClick: (Event) -> Unit
): ListAdapter<Event, UpcomingEventsViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingEventsViewHolder {
        val binding =
            ItemUpcommingEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingEventsViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: UpcomingEventsViewHolder, position: Int) {
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

class UpcomingEventsViewHolder(
    private val binding: ItemUpcommingEventBinding,
    private val onItemClick: (Event) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Event) {
        binding.item = item
        itemView.setOnClickListener {
            onItemClick(item)
        }
    }

}