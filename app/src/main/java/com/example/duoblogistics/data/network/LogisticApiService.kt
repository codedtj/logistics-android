package com.example.duoblogistics.data.network

import com.example.duoblogistics.data.db.entities.Branch
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemWithInfo
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.data.network.interceptors.RequestTokenInterceptor
import com.example.duoblogistics.data.network.interceptors.ResponseCodeInterceptor
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import com.example.duoblogistics.internal.utils.SharedSettings
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LogisticApiService {
    @POST("authorize")
    fun authorize(@Body data: Credentials): Single<AuthenticationResponse>

    @GET("available-trips")
    fun getTrips(): Flowable<List<Trip>>

    @GET("trips/{id}/items")
    fun getTripStoredItems(@Path("id") tripId: String): Flowable<List<StoredItemWithInfo>>

    @GET("branches")
    fun getBranches() : Flowable<List<Branch>>

/*    @GET("user")
    suspend fun getAuthorizedUser(): Response<AuthorizedUserResponse>



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
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://duob.ajoibot.tj/api/")
                .client(client)
                .build()

            return retrofit.create(LogisticApiService::class.java)
        }
    }
}