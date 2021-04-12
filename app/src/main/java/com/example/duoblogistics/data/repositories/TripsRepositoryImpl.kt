package com.example.duoblogistics.data.repositories

import android.util.Log
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemInfo
import com.example.duoblogistics.data.db.entities.StoredItemWithInfo
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.network.RemoteDataSource
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

class TripsRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : TripsRepository {
    override fun getTrips(): Flowable<List<Trip>> {
//        val disposable = remote.fetchTrips()
//            .subscribeOn(Schedulers.computation())
//            .subscribe(
//                { trips ->
//                    Log.d("trips-repository", "Trips are loaded from remote $trips")
//                    local.saveTrips(trips)
//                        .subscribeOn(Schedulers.computation())
//                        .subscribe(
//                            {
//                                Log.d("trips-repository", "Trips are saved. Rows: $it")
//                            },
//                            {
//                                Log.e("trips-repository", "Failed to save trips: $it")
//                            }
//                        )
//                },
//                { e ->
//                    Log.e("trips-repository", "Failed to load trip from remote $e")
//                }
//            )
        return remote.fetchTrips().switchMapSingle {
            Log.d("trips-repository", "Saving trips to db $it")
            local.saveTrips(it)
        }.switchMap {
            local.getTrips()
        }
    }

    override fun fetchTripStoredItems(id: String): Flowable<List<Long>> {
        return remote.fetchTripStoredItems(id)
            .switchMapSingle { saveStoredItemsWithInfo(it) }
    }

    override fun getTripStoredItems(id: String): Flowable<List<StoredItem>> {
//        val disposable = remote.fetchTripStoredItems(id)
//            .subscribeOn(Schedulers.newThread())
//            .subscribe(
//                { storedItemWitnInfos ->
//                    saveStoredItemsWithInfo(storedItemWitnInfos)
//                },
//                { e ->
//                    Log.e("trips-repository", "Failed to load trip stored items from remote $e")
//                }
//            )

        return local.getTripStoredItems(id)
    }

    override fun getStoredItemInfo(id: String): Maybe<StoredItemInfo> = local.getStoredItemInfo(id)

    private fun saveStoredItemsWithInfo(storedItemsWithInfo: List<StoredItemWithInfo>): Single<List<Long>> {
        return local.saveStoredItemInfos(storedItemsWithInfo.map { it.info })
            .flatMap { local.getStoredItemsById(storedItemsWithInfo.map { it.id }) }
            .flatMap { existingStoredItems ->
                Log.d("trips-repository", "Existing stored items : $existingStoredItems")

                local.saveStoredItems(storedItemsWithInfo.map { storedItemsWithInfo ->
                    StoredItem(
                        storedItemsWithInfo.id,
                        storedItemsWithInfo.code,
                        storedItemsWithInfo.status,
                        storedItemsWithInfo.stored_item_info_id,
                        storedItemsWithInfo.trip_id,
                        storedItemsWithInfo.trip_status,
                        existingStoredItems.firstOrNull { it.id == storedItemsWithInfo.id }?.scanned == true
                    )
                })
            }
    }

//    private fun saveStoredItemsWithInfo(storedItemsWithInfo: List<StoredItemWithInfo>) {
//        val o = local.saveStoredItemInfos(storedItemsWithInfo.map { it.info })
//            .doOnDispose { Log.d("dispose-saveSItemInfos", "I am disposed") }
//            .doOnError { Log.d("dispose-saveSItemInfos", "I have an error") }
//            .doFinally { Log.d("dispose-saveSItemInfos", "I have finished") }
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                {
//                    Log.d(
//                        "trips-repository",
//                        "Trip stored item info are saved. $storedItemsWithInfo Rows: $it"
//                    )
//                },
//                {
//                    Log.e("trips-repository", "Failed to save trip stored items info: $it")
//                }
//            )
//
//        val d = local.getStoredItemsById(storedItemsWithInfo.map { it.id })
//            .doOnCancel { Log.d("dispose-getSItemsById", "I am canceled") }
//            .doOnError { Log.d("dispose-getSItemsById", "I have an error") }
//            .doOnComplete { Log.d("dispose-getSItemsById", "I have completed") }
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { existingStoredItems ->
//                    Log.d("trips-repository", "Existing stored items : $existingStoredItems")
//                    val items = storedItemsWithInfo.map { storedItemsWithInfo ->
//                        StoredItem(
//                            storedItemsWithInfo.id,
//                            storedItemsWithInfo.code,
//                            storedItemsWithInfo.status,
//                            storedItemsWithInfo.stored_item_info_id,
//                            storedItemsWithInfo.trip_id,
//                            storedItemsWithInfo.trip_status,
//                            existingStoredItems.firstOrNull { it.id == storedItemsWithInfo.id }?.scanned == true
//                        )
//                    }
//
//                    saveStoredItems(items)
//                },
//                {
//                    Log.e("trips-repository", "Failed to get stored items by id: $it")
//                }
//            )
//    }

//    private fun saveStoredItems(storedItems: List<StoredItem>) {
//        return local.saveStoredItems(storedItems)
//            .doOnDispose { Log.d("dispose-saveStoredItems", "I am disposed") }
//            .doFinally { Log.d("dispose-saveStoredItems", "I have finished") }
//            .subscribe(
//                {
//                    Log.d("trips-repository", "Trip stored items are saved. Rows: $it")
//                },
//                {
//                    Log.e("trips-repository", "Failed to save trip stored items: $it")
//                }
//            ).dispose()
//    }
}