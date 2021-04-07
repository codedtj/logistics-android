package com.example.duoblogistics.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import com.example.duoblogistics.data.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _authorizeResponse = MutableLiveData<AuthenticationResponse>()
    val authorizeResponse: LiveData<AuthenticationResponse>
        get() = _authorizeResponse

    fun authorize(credentials: Credentials) {
        var disposable = authRepository.authorize(credentials)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    _authorizeResponse.postValue(response)
                    Log.d("login", "Success ${response.toString()}")
                },
                { error ->
                    Log.e("login", "Failed  ${error.message}")
                }
            )
    }

    /**
     *Return authentication token
     */
//    fun authorize(credentials: Credentials){
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = repository.authorize(credentials)
//            _authorizeResponse.postValue(response)
//
//            Log.d("auth", "Token required $response")
//        }
//    }
}