package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import io.reactivex.Flowable
import io.reactivex.Single

interface RemoteDataSource {
    fun authorize(credentials: Credentials): Single<AuthenticationResponse>

//    suspend fun getAuthorizedUser(): Result<AuthorizedUserResponse>
//
    fun fetchTrips(): Flowable<List<Trip>>

    fun fetchTripItems(id:String) : Flowable<List<StoredItem>>
//
//    suspend fun loadItem(tripId: String, itemCode: String)
//
//    suspend fun unloadItem(tripId: String, itemCode: String)
//
//    suspend fun transferItem(tripId: String, targetTripId: String, itemCode: String)
//
//    suspend fun getStoredItem(code: String): Result<StoredItem>
}