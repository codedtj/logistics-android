package com.example.duoblogistics.ui.trips

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemInfo
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.repositories.StoredItemRepository
import com.example.duoblogistics.data.repositories.TripsRepository
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TripsViewModel(
    private val tripsRepository: TripsRepository,
    private val storedItemRepository: StoredItemRepository
) : BaseViewModel() {
    var selectedTrip: Trip? = null

    var selectedStoredItem: StoredItem? = null

    private val mTrips = MutableLiveData<List<Trip>>()
    val trips: LiveData<List<Trip>>
        get() = mTrips

    private val mStoredItems = MutableLiveData<List<StoredItem>>()
    val storedItems: LiveData<List<StoredItem>>
        get() = mStoredItems

    private val mStoredItemInfo = MutableLiveData<StoredItemInfo>()
    val storedItemInfo: LiveData<StoredItemInfo>
        get() = mStoredItemInfo

    fun fetchTrips() {
        compositeDisposable += tripsRepository.fetchTrips()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d("trips-view-model", "Trips are fetched $it")
                },
                {
                    Log.e("trips-view-model", "Failed to fetch trips $it")
                }
            )
    }

    fun getTrips() {
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

    fun refreshStoredItems(tripId: String) {
        Log.d("trips-view-model", "Refreshing trip stored items")
        compositeDisposable += tripsRepository.fetchTripStoredItems(tripId)
            .doOnCancel { Log.d("dispose-sVMStoredItems", "I am canceled") }
            .doOnComplete { Log.d("dispose-sVMStoredItems", "I have completed") }
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d("trips-view-model", "Trips stored items were refreshed $it")
                },
                {
                    Log.e("trips-view-model", "Failed to refresh trip stored items $it")
                }
            )
    }

    fun getTripStoredItems(tripId: String) {
        compositeDisposable += tripsRepository.getTripStoredItems(tripId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d("trips-view-model", "Loaded trip stored items $it")
//                    if (it.firstOrNull()?.trip_id == selectedTrip?.id)
                        mStoredItems.postValue(it)
                },
                {
                    Log.e("trips-view-model", "Failed to load trip stored items $it")
                }
            )
    }

//    fun clearTripStoredItems() {
//        Log.d("trips-view-model", "Clear trip stored items list")
//        this.mStoredItems.postValue(emptyList())
//    }

    fun getStoredItemInfo(id: String) {
        compositeDisposable += tripsRepository.getStoredItemInfo(id)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mStoredItemInfo.postValue(it)
                },
                {
                    Log.e("trips-view-model", "Failed to load stored item info $it")
                }
            )
    }

    fun scanStoredItem(code: String) {
        this.mStoredItems.value?.apply {
            val storedItem = firstOrNull { it.code == code }
            storedItem?.apply {
                storedItem.scanned = true
                storedItemRepository.updateStoredItem(storedItem)
                    .subscribeOn(Schedulers.computation())
                    .subscribe(
                        {
                            Log.d("trips-view-model", "Stored item updated $storedItem ")
                        },
                        {
                            Log.e("trips-view-model", "Failed to update stored item $storedItem")
                        }
                    )
            }
        }
    }
}