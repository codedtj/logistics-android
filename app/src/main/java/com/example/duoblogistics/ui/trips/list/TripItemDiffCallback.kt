package com.example.duoblogistics.ui.trips.list

import androidx.recyclerview.widget.DiffUtil
import com.example.duoblogistics.data.entities.Trip

class TripItemDiffCallback : DiffUtil.ItemCallback<Trip>() {
    override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean = oldItem.id === newItem.id

    override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean =
        oldItem.status == newItem.status
}