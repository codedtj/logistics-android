package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.db.entities.*
import com.example.duoblogistics.data.network.models.ActionWithItemsList
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import com.example.duoblogistics.data.network.models.Response
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface RemoteDataSource {
    fun authorize(credentials: Credentials): Single<AuthenticationResponse>

    //    suspend fun getAuthorizedUser(): Result<AuthorizedUserResponse>
//
    fun fetchTrips(): Flowable<List<Trip>>

    fun fetchTripStoredItems(id: String): Flowable<List<StoredItemWithInfo>>

    fun fetchBranches(): Flowable<List<Branch>>

    fun postAction(action: ActionWithItemsList): Completable


//
//    suspend fun loadItem(tripId: String, itemCode: String)
//
//    suspend fun unloadItem(tripId: String, itemCode: String)
//
//    suspend fun transferItem(tripId: String, targetTripId: String, itemCode: String)
//
//    suspend fun getStoredItem(code: String): Result<StoredItem>
}