package com.example.duoblogistics.ui.trips.actions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.Branch
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.schedulers.Schedulers

class SelectActionViewModel(
    private val localDataSource: LocalDataSource
) : BaseViewModel() {
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

    private val mBranches = MutableLiveData<List<Branch>>()
    val branches: LiveData<List<Branch>>
        get() = mBranches

    fun setSelectedTrip(trip: Trip) {
        mSelectedTrip.postValue(trip)
    }

    fun setSelectedAction(action: String) {
        mSelectedAction.postValue(action)
    }

    fun setSelectedBranch(branch: Branch){
        mSelectedBranch.postValue(branch)
    }

    fun setErrorMessage(message: String) {
        mErrorMessage.postValue(message)
    }

    fun getBranches() {
        compositeDisposable += localDataSource.getBranches()
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    mBranches.postValue(it)
                    Log.d("select-action-vm", "Branches are loaded $it")
                },
                {
                    Log.e("select-action-vm", "Failed to get branches $it")
                }
            )
    }
}