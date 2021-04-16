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
import okhttp3.Response
import java.io.IOException

class ResponseCodeInterceptor(private val context: Context):Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.code()) {
            in 500..526 -> {
                throw HttpServerErrorException("Сервер временно недоступен")
            }
            404 -> {
                throw HttpNotFoundException("Запрашиваемый ресурс не найден")
            }
            403 -> {
                throw HttpForbiddenException("Нет прав доступа к ресурсу")
            }
            401 -> {
                val activityIntent = Intent(context, LoginActivity::class.java)
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(activityIntent)
                throw HttpUnauthorizedException("Ошибка авторизации")
            }
        }

        Log.d("network-layer", ("${response.code()} ${chain.request().url()}"))
        return response
    }

}