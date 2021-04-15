package com.example.duoblogistics.ui.trips.actions

import android.view.View
import android.widget.AdapterView
import com.example.duoblogistics.data.db.entities.Trip

class ActionSpinnerOnSelectedListener(
    private val selectActionViewModel: SelectActionViewModel,
    private val actions: List<String>
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectActionViewModel.setSelectedAction(
            actions[position]
        )
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}