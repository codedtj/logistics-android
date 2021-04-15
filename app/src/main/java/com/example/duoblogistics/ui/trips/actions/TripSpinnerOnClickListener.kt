package com.example.duoblogistics.ui.trips.actions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.duoblogistics.data.db.entities.Trip

class TripSpinnerOnClickListener(
    private val selectActionViewModel: SelectActionViewModel,
    private val trips: List<Trip>
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectActionViewModel.setSelectedTrip(trips[position])
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}