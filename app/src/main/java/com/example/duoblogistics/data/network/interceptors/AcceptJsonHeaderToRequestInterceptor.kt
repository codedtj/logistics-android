package com.example.duoblogistics.data.network.interceptors

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.duoblogistics.data.network.HttpForbiddenException
import com.example.duoblogistics.data.network.HttpNotFoundException
import com.example.duoblogistics.data.network.HttpServerErrorException
import com.example.duoblogistics.data.network.HttpUnauthorizedException
import com.example.duoblogistics.ui.auth.LoginActivity
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AcceptJsonHeaderToRequestInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request = request.newBuilder()
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }

}