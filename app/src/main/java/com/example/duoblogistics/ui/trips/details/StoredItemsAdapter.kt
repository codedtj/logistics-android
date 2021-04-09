package com.example.duoblogistics.ui.trips.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.databinding.ViewholderStoredItemBinding
import com.example.duoblogistics.ui.trips.TripsViewModel

class StoredItemsAdapter (
    private val fragment: Fragment,
    private val vm: TripsViewModel
        ): ListAdapter<StoredItem, StoredItemViewHolder>(StoredItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoredItemViewHolder {
        return StoredItemViewHolder(
            ViewholderStoredItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoredItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        val dataBinding = holder.getDataBinding()
//        dataBinding.handler = TripClickHandler(fragment, item, vm)
    }
}


