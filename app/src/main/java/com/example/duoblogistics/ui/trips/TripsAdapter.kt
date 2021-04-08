package com.example.duoblogistics.ui.trips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.duoblogistics.data.entities.Trip
import com.example.duoblogistics.databinding.ViewholderTripBinding

class TripsAdapter : ListAdapter<Trip, TripViewHolder>(TripItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            ViewholderTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


