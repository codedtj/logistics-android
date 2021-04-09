package com.example.duoblogistics.ui.trips.details

import androidx.recyclerview.widget.DiffUtil
import com.example.duoblogistics.data.db.entities.StoredItem

class StoredItemDiffCallback : DiffUtil.ItemCallback<StoredItem>() {
    override fun areItemsTheSame(oldItem: StoredItem, newItem: StoredItem): Boolean =
        oldItem.id === newItem.id

    override fun areContentsTheSame(oldItem: StoredItem, newItem: StoredItem): Boolean =
        oldItem.scanned == newItem.scanned
}