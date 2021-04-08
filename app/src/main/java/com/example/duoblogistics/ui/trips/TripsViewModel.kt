package com.example.duoblogistics.ui.trips

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.duoblogistics.BR
import com.example.duoblogistics.R
import com.example.duoblogistics.data.entities.Trip
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.repositories.TripsRepository
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.data.RecyclerItem
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TripsViewModel(private val tripsRepository: TripsRepository) : BaseViewModel() {
//    private val mTrips = MutableLiveData<List<Trip>>()
//    val trips: LiveData<List<Trip>>
//        get() = mTrips

    private val mData = MutableLiveData<List<RecyclerItem>>()
    val data: LiveData<List<RecyclerItem>>
        get() = mData


    fun fetchTrips() {
        compositeDisposable += tripsRepository
            .getActiveTrips()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("trips-view-model", "Loaded trips $it")
                    val trips = it.map { trip ->
                        RecyclerItem(
                            data = trip,
                            variableId = BR.trip,
                            layoutId = R.layout.viewholder_trip
                        )
                    }

                    mData.postValue(trips)
                },
                {
                    Log.e("trips-view-model", "Failed to load trips $it")
                }
            )
//        trips.map {
//                trip ->
//            RecyclerItem(
//                data = trip,
//                variableId = BR.trip,
//                layoutId = R.layout.viewholder_trip
//            )
//        }
    }
}