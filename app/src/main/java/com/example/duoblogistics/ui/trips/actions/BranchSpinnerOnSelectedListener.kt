package com.example.duoblogistics.ui.trips.actions

import android.view.View
import android.widget.AdapterView
import com.example.duoblogistics.data.db.entities.Branch

class BranchSpinnerOnSelectedListener(
    private val selectActionViewModel: SelectActionViewModel,
    private val branches: List<Branch>
): AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectActionViewModel.setSelectedBranch(
            branches[position]
        )
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}