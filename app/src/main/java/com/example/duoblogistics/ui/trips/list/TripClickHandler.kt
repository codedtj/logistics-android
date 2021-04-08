package com.example.duoblogistics.ui.trips.list

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.duoblogistics.data.entities.Trip
import com.example.duoblogistics.internal.interfaces.ElementClickHandler
import com.example.duoblogistics.ui.trips.TripsViewModel

class TripClickHandler  (
    private val fragment: Fragment,
    private val trip: Trip,
    private val vm: TripsViewModel
): ElementClickHandler {

    override fun onElementClick() {
        vm.selectedTrip = trip
        val action = TripsFragmentDirections.showTripDetails()
        fragment.findNavController().navigate(action)

    }
}