package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.entities.Trip
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import io.reactivex.Flowable
import io.reactivex.Single

class RemoteDataSourceImpl(
    private val apiService: LogisticApiService
) : RemoteDataSource {
    override fun authorize(credentials: Credentials): Single<AuthenticationResponse> =
        apiService.authorize(credentials)

    override fun getActiveTrips(): Flowable<List<Trip>>  = apiService.getActiveTrips()
}