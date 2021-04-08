package com.example.duoblogistics.ui.trips

import androidx.recyclerview.widget.RecyclerView
import com.example.duoblogistics.data.entities.Trip
import com.example.duoblogistics.databinding.ViewholderTripBinding

class TripViewHolder(private val dataBinding: ViewholderTripBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(item: Trip) {
        dataBinding.trip = item
        dataBinding.executePendingBindings()
    }

    fun getDataBinding(): ViewholderTripBinding {
        return dataBinding;
    }
}