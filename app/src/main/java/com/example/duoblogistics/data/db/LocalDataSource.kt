package com.example.duoblogistics.data.db

import com.example.duoblogistics.data.db.daos.TripDao
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.Trip
import io.reactivex.Flowable
import io.reactivex.Single

interface LocalDataSource {
    fun getTrips(): Flowable<List<Trip>>

    fun saveTrips(trips:List<Trip>): Single<List<Long>>

    fun getTripStoredItems(id:String): Flowable<List<StoredItem>>
}