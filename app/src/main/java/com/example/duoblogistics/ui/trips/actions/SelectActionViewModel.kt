package com.example.duoblogistics.ui.trips.actions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duoblogistics.data.db.entities.Trip

class SelectActionViewModel: ViewModel() {
    private val mSelectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip>
        get() = mSelectedTrip

    fun setSelectedTrip(trip:Trip){
        mSelectedTrip.postValue(trip)
    }
}