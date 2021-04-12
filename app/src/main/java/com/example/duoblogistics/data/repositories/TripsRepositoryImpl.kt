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
//        return remote.fetchTrips().switchMapSingle {
//            Log.d("trips-repository", "Saving trips to db $it")
//            local.saveTrips(it)
//        }.switchMap {
//            local.getTrips()
//        }

        return  local.getTrips().doOnNext{
            remote.fetchTrips().switchMapSingle{ local.saveTrips(it) }
        }
    }

    override fun fetchTripStoredItems(id: String): Flowable<List<Long>> {
        return remote.fetchTripStoredItems(id)
            .switchMapSingle { saveStoredItemsWithInfo(it) }
    }

    override fun getTripStoredItems(id: String): Flowable<List<StoredItem>> {
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
}