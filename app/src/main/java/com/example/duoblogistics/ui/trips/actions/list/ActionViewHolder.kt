package com.example.duoblogistics.ui.trips.actions.list

import androidx.recyclerview.widget.RecyclerView
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.databinding.ViewholderActionBinding
import com.example.duoblogistics.databinding.ViewholderStoredItemBinding

class ActionViewHolder(private val dataBinding: ViewholderActionBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(item: Action) {
        dataBinding.action= item
        dataBinding.executePendingBindings()
    }

    fun getDataBinding(): ViewholderActionBinding {
        return dataBinding;
    }
}