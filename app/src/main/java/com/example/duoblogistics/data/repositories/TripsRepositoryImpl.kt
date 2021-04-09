package com.example.duoblogistics.data.repositories

import android.util.Log
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.network.RemoteDataSource
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class TripsRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : TripsRepository {
    override fun getTrips(): Flowable<List<Trip>> {
        val disposable = remote.fetchTrips()
            .subscribeOn(Schedulers.computation())
            .subscribe(
                { trips ->
                    Log.d("trips-repository", "Trips are loaded from remote $trips")
                    local.saveTrips(trips)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(
                            {
                                Log.d("trips-repository", "Trips are saved. Rows: $it")
                            },
                            {
                                Log.e("trips-repository", "Failed to save trips: $it")
                            }
                        )
                },
                { e ->
                    Log.e("trips-repository", "Failed to load trip from remote $e")
                }
            )

        return local.getTrips()
    }

    override fun getTripStoredItems(id: String): Flowable<List<StoredItem>> {
        val disposable = remote.fetchTripStoredItems(id)
            .subscribeOn(Schedulers.computation())
            .subscribe(
                { storedItems ->
                    Log.d(
                        "trips-repository",
                        "Trip stored items are loaded from remote $storedItems"
                    )
                    local.saveStoredItems(storedItems)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(
                            {
                                Log.d("trips-repository", "Trip stored items are saved. Rows: $it")
                            },
                            {
                                Log.e("trips-repository", "Failed to save trip stored items: $it")
                            }
                        )
                },
                { e ->
                    Log.e("trips-repository", "Failed to load trip stored items from remote $e")
                }
            )

        return local.getTripStoredItems(id)
    }
}