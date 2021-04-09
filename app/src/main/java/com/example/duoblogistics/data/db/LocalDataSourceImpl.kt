package com.example.duoblogistics.data.db

import android.util.Log
import com.example.duoblogistics.data.db.daos.TripDao
import com.example.duoblogistics.data.db.entities.Trip
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSourceImpl(
    private val tripDao: TripDao
) : LocalDataSource {
    override fun getTrips(): Flowable<List<Trip>> = tripDao.getTrips()

    override fun saveTrips(trips: List<Trip>): Single<List<Long>> {
        Log.d("trips-local-data-source", "Saving trips $trips")
       return tripDao.deleteTripsWhereNotIn(trips.map { it.id }).andThen(tripDao.insert(trips))
    }
}