package com.example.duoblogistics.ui.trips.actions.list

import androidx.recyclerview.widget.DiffUtil
import com.example.duoblogistics.data.db.entities.Action

class ActionDiffCallback : DiffUtil.ItemCallback<Action>() {
    override fun areItemsTheSame(oldItem: Action, newItem: Action): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Action, newItem: Action): Boolean =
        oldItem.status == newItem.status
}