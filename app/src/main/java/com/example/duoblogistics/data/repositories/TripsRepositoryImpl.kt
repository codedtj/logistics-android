package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.entities.Trip
import com.example.duoblogistics.data.network.RemoteDataSource
import io.reactivex.Flowable

class TripsRepositoryImpl(private val remote: RemoteDataSource) : TripsRepository {
    override fun getActiveTrips(): Flowable<List<Trip>> {
        return remote.getActiveTrips()
    }
}