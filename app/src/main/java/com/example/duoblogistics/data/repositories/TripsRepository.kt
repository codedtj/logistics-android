package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemInfo
import com.example.duoblogistics.data.db.entities.Trip
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface TripsRepository {
    fun getTrips(): Flowable<List<Trip>>

    fun getTripStoredItems(id:String): Flowable<List<StoredItem>>

    fun getStoredItemInfo(id:String): Maybe<StoredItemInfo>

//    suspend fun loadItem(tripId: String, itemCode: String)
//
//    suspend fun unloadItem(tripId: String, itemCode: String)
//
//    suspend fun transferItem(tripId: String, targetTripId: String, itemCode: String)
}