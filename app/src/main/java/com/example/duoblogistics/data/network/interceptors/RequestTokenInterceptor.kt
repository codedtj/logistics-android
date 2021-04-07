package com.example.duoblogistics.data.network.interceptors

import android.util.Log
import com.example.duoblogistics.internal.utils.SharedSettings
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RequestTokenInterceptor(private val settings: SharedSettings) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request

        val token = settings.getToken()

        if (!token.isNullOrBlank()) {
            newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            Log.d("auth", "Token attached $token")

            return chain.proceed(newRequest)
        }

        return chain.proceed(request)
    }
}