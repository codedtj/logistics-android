package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.network.interceptors.RequestTokenInterceptor
import com.example.duoblogistics.data.network.interceptors.ResponseCodeInterceptor
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import com.example.duoblogistics.internal.utils.SharedSettings
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface LogisticApiService {
    @POST("authorize")
    fun authorize(@Body data: Credentials): Single<AuthenticationResponse>

/*    @GET("user")
    suspend fun getAuthorizedUser(): Response<AuthorizedUserResponse>

    @GET("trips")
    suspend fun getActiveTrips(): Response<List<ActiveTrip>>

    @POST("trip/{id}/load")
    suspend fun loadItem(
        @Path("id") tripId: String,
        @Query("stored_item") itemCode: String
    )

    @POST("trip/{id}/unload")
    suspend fun unloadItem(
        @Path("id") tripId: String,
        @Query("stored_item") itemCode: String
    )

    @POST("trip/{id}/transfer/{targetTrip}")
    suspend fun transferItem(
        @Path("id") tripId: String,
        @Path("targetTrip") targetTripId: String,
        @Query("stored_item") itemCode: String
    )

    @GET("stored-item")
    suspend fun getStoredItem(
        @Query("code") code: String
    ): Response<StoredItem>*/

    companion object {
        operator fun invoke(settings: SharedSettings
        ): LogisticApiService {

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(RequestTokenInterceptor(settings))
                .addInterceptor(ResponseCodeInterceptor())

            val client = httpClient.build()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://duob.ajoibot.tj/api/")
                .client(client)
                .build()

            return retrofit.create(LogisticApiService::class.java)
        }
    }
}