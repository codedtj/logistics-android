package com.example.duoblogistics.ui.trips.actions.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.ActionWithStoredItems
import com.example.duoblogistics.data.db.entities.Branch
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.repositories.ActionRepository
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.schedulers.Schedulers

class ActionDetailViewModel(
    private val actionRepository: ActionRepository,
    private val localDataSource: LocalDataSource
) : BaseViewModel() {
    private val mActionWithStoredItems = MutableLiveData<ActionWithStoredItems>()
    val actionWithStoredItems: LiveData<ActionWithStoredItems>
        get() = mActionWithStoredItems

    private val mTrip = MutableLiveData<Trip>()
    val trip: LiveData<Trip>
        get() = mTrip

    private val mTripTo = MutableLiveData<Trip>()
    val tripTo: LiveData<Trip>
        get() = mTripTo

    private val mBranch = MutableLiveData<Branch>()
    val branch: LiveData<Branch>
        get() = mBranch

    fun getActionStoredItems(id: Long) {
        compositeDisposable += actionRepository.getActionWithStoredItems(id)
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    mActionWithStoredItems.postValue(it)
                    loadRelations(it.action)
                },
                {
                    Log.e("actions-vm", "Failed to load action with stored items $it")
                }
            )
    }

    private fun loadRelations(action: Action) {
        if (action.branchToId != null)
            compositeDisposable += localDataSource.getBranch(action.branchToId)
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        mBranch.postValue(it)
                    },
                    {
                        Log.e("actions-vm", "Failed to load action branch $it")
                    }
                )

        compositeDisposable += localDataSource.getTrip(action.tripId)
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    mTrip.postValue(it)
                },
                {
                    Log.e("actions-vm", "Failed to load action trip $it")
                }
            )

        if (action.tripToId != null)
            compositeDisposable += localDataSource.getTrip(action.tripToId)
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        mTripTo.postValue(it)
                    },
                    {
                        Log.e("actions-vm", "Failed to load action trip TO $it")
                    }
                )

    }
}