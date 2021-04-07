package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import io.reactivex.Single

interface IRemoteDataSource {
    fun authorize(credentials: Credentials): Single<AuthenticationResponse>

//    suspend fun getAuthorizedUser(): Result<AuthorizedUserResponse>
//
//    suspend fun getActiveTrips(): Result<List<ActiveTrip>>
//
//    suspend fun loadItem(tripId: String, itemCode: String)
//
//    suspend fun unloadItem(tripId: String, itemCode: String)
//
//    suspend fun transferItem(tripId: String, targetTripId: String, itemCode: String)
//
//    suspend fun getStoredItem(code: String): Result<StoredItem>
}