package com.example.duoblogistics.data.repositories

import android.util.Log
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemInfo
import com.example.duoblogistics.data.db.entities.StoredItemWithInfo
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.network.RemoteDataSource
import io.reactivex.Flowable
import io.reactivex.Single
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
                { storedItemWitnInfos ->
                    saveStoredItemsWithInfo(storedItemWitnInfos)
                },
                { e ->
                    Log.e("trips-repository", "Failed to load trip stored items from remote $e")
                }
            )

        return local.getTripStoredItems(id)
    }

    override fun getStoredItemInfo(id: String): Single<StoredItemInfo> = local.getStoredItemInfo(id)


    private fun saveStoredItemsWithInfo(storedItemsWithInfo: List<StoredItemWithInfo>) {
        val o = local.saveStoredItemInfos(storedItemsWithInfo.map { it.info })
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    Log.d("trips-repository", "Trip stored item info are saved. Rows: $it")
                },
                {
                    Log.e("trips-repository", "Failed to save trip stored items info: $it")
                }
            )

        val d = local.saveStoredItems(storedItemsWithInfo.map {
            StoredItem(
                it.id,
                it.code,
                it.status,
                it.stored_item_info_id,
                it.trip_id,
                it.trip_status,
                it.scanned
            )
        }).subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    Log.d("trips-repository", "Trip stored items are saved. Rows: $it")
                },
                {
                    Log.e("trips-repository", "Failed to save trip stored items: $it")
                }
            )

    }
}