package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.network.RemoteDataSource
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import io.reactivex.Single

class AuthRepositoryImpl(private val remote: RemoteDataSource): AuthRepository{
    override fun authorize(credentials: Credentials): Single<AuthenticationResponse> {
        return remote.authorize(credentials);
    }

//    suspend fun getAuthorizedUser(): Result<AuthorizedUserResponse>{
//        return remote.getAuthorizedUser()
//    }
}