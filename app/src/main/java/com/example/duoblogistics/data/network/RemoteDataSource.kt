package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import io.reactivex.Single

class RemoteDataSource(
    private val apiService: LogisticApiService
) : IRemoteDataSource {
    override fun authorize(credentials: Credentials): Single<AuthenticationResponse> =
        apiService.authorize(credentials)
}