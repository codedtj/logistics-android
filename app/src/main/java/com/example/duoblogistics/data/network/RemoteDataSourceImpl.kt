package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.db.entities.*
import com.example.duoblogistics.data.network.models.ActionWithItemsList
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import com.example.duoblogistics.data.network.models.Response
import io.reactivex.Completable
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

    override fun fetchBranches(): Flowable<List<Branch>> = apiService.getBranches()

    override fun postAction(action: ActionWithItemsList): Single<Response> = apiService.postAction(action)
}