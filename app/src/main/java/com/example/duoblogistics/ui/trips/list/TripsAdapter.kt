package com.example.duoblogistics.ui.trips.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.databinding.ViewholderTripBinding
import com.example.duoblogistics.ui.trips.TripsViewModel

class TripsAdapter (
    private val fragment: Fragment,
    private val vm: TripsViewModel
        ): ListAdapter<Trip, TripViewHolder>(TripItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            ViewholderTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        val dataBinding = holder.getDataBinding()
        dataBinding.handler = TripClickHandler(fragment, item, vm)
    }
}


