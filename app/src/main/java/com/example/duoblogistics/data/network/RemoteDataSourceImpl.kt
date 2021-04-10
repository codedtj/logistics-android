package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemWithInfo
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import io.reactivex.Flowable
import io.reactivex.Single

class RemoteDataSourceImpl(
    private val apiService: LogisticApiService
) : RemoteDataSource {
    override fun authorize(credentials: Credentials): Single<AuthenticationResponse> =
        apiService.authorize(credentials)

    override fun fetchTrips(): Flowable<List<Trip>> = apiService.getTrips()

    override fun fetchTripStoredItems(id: String): Flowable<List<StoredItemWithInfo>> =
        apiService.getTripStoredItems(id)
}