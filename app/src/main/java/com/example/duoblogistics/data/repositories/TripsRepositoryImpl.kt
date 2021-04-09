package com.example.duoblogistics.data.repositories

import android.util.Log
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.network.RemoteDataSource
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class TripsRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : TripsRepository {
    override fun getTrips(): Flowable<List<Trip>> {
        val disposable = remote.getTrips()
            .subscribeOn(Schedulers.computation())
            .subscribe(
                { trips ->
                    Log.d("trips-repository", "Trips are loaded from remote $trips")
                    local.saveTrips(trips)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(
                            {
                                Log.d("trips-repository", "Trips are save rows: $it")
                            },
                            {
                                Log.e("trips-repository", "Failed to save trips: $it")
                            }
                        )
                },
                { e ->
                    Log.e("trips-repository", "Failed to load from remote $e")
                }
            )

        return local.getTrips()
    }
}