package com.example.duoblogistics.ui.trips

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.repositories.TripsRepository
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TripsViewModel(private val tripsRepository: TripsRepository) : BaseViewModel() {
    var selectedTrip: Trip? = null

    private val mTrips = MutableLiveData<List<Trip>>()
    val trips: LiveData<List<Trip>>
        get() = mTrips

    private val mStoredItems = MutableLiveData<List<StoredItem>>()
    val storedItems: LiveData<List<StoredItem>>
        get() = mStoredItems

    fun fetchTrips() {
        compositeDisposable += tripsRepository
            .getTrips()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("trips-view-model", "Loaded trips $it")

                    mTrips.postValue(it)
                },
                {
                    Log.e("trips-view-model", "Failed to load trips $it")
                }
            )
    }

    fun fetchTripStoredItems(id: String){
        compositeDisposable += tripsRepository
            .getTripStoredItems(id)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("trips-view-model", "Loaded trip stored items $it")

                    mStoredItems.postValue(it)
                },
                {
                    Log.e("trips-view-model", "Failed to load trip stored items $it")
                }
            )
    }

    fun clearTripStoredItems(){
        this.mStoredItems.postValue(null)
    }
}