package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.db.entities.Trip
import io.reactivex.Flowable

interface TripsRepository {
    fun getTrips(): Flowable<List<Trip>>

//    suspend fun loadItem(tripId: String, itemCode: String)
//
//    suspend fun unloadItem(tripId: String, itemCode: String)
//
//    suspend fun transferItem(tripId: String, targetTripId: String, itemCode: String)
}