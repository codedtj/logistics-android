package com.example.duoblogistics.ui.trips.actions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duoblogistics.data.db.entities.Branch
import com.example.duoblogistics.data.db.entities.Trip

class SelectActionViewModel: ViewModel() {
    private val mSelectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip>
        get() = mSelectedTrip

    private val mSelectedBranch = MutableLiveData<Branch>()
    val selectedBranch: LiveData<Branch>
        get() = mSelectedBranch

    private val mSelectedAction = MutableLiveData<String>()
    val selectedAction: LiveData<String>
        get() = mSelectedAction

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = mErrorMessage

    fun setSelectedTrip(trip:Trip){
        mSelectedTrip.postValue(trip)
    }

    fun setSelectedAction(action: String){
        mSelectedAction.postValue(action)
    }

    fun setErrorMessage(message: String){
        mErrorMessage.postValue(message)
    }
}