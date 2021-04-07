package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import io.reactivex.Single

interface AuthRepository {
    fun authorize(credentials: Credentials): Single<AuthenticationResponse>;
}