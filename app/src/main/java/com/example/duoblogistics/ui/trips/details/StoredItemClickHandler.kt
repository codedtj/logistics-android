package com.example.duoblogistics.ui.trips.details

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.internal.interfaces.ElementClickHandler
import com.example.duoblogistics.ui.trips.TripsViewModel

class StoredItemClickHandler(
    private val fragment: Fragment,
    private val storedItem: StoredItem,
    private val vm: TripsViewModel
): ElementClickHandler {
    override fun onElementClick() {
        vm.selectedStoredItem = storedItem
        val action = TripFragmentDirections.showStoredItemDetails(storedItem.code)
        fragment.findNavController().navigate(action)
    }
}