package com.example.duoblogistics.ui.trips.details

import androidx.recyclerview.widget.RecyclerView
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.databinding.ViewholderStoredItemBinding

class StoredItemViewHolder(private val dataBinding: ViewholderStoredItemBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(item: StoredItem) {
        dataBinding.storedItem= item
        dataBinding.executePendingBindings()
    }

    fun getDataBinding(): ViewholderStoredItemBinding {
        return dataBinding;
    }
}