package com.example.duoblogistics.ui.trips.actions.list

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.internal.interfaces.ElementClickHandler
import com.example.duoblogistics.ui.trips.TripsViewModel
import com.example.duoblogistics.ui.trips.actions.ActionsViewModel

class ActionClickHandler(
    private val fragment: Fragment,
    private val vm: ActionsViewModel,
    private val action: Action
): ElementClickHandler {
    override fun onElementClick() {
        vm.selectedAction = action
        fragment.findNavController().navigate(
            ActionsListFragmentDirections.navigateToActionDetails()
        )
    }
}