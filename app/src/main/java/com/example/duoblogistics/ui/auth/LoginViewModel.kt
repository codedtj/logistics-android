package com.example.duoblogistics.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.duoblogistics.data.network.models.AuthenticationResponse
import com.example.duoblogistics.data.network.models.Credentials
import com.example.duoblogistics.data.repositories.AuthRepository
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.data.STATUS_BUSY
import com.example.duoblogistics.internal.data.STATUS_NEEDLE
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    private val _authorizeResponse = MutableLiveData<AuthenticationResponse>()
    val authorizeResponse: LiveData<AuthenticationResponse>
        get() = _authorizeResponse

    private val _state = MutableLiveData<String>();
    val state: LiveData<String>
        get() = _state

    private fun setBusy(){
        _state.postValue(STATUS_BUSY)
    }

    private fun setNeedle(){
        _state.postValue(STATUS_NEEDLE)
    }

    fun authorize(credentials: Credentials) {
        setBusy()
        compositeDisposable += authRepository.authorize(credentials)
            .doFinally {
                setNeedle()
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    _authorizeResponse.postValue(response)
                    Log.d("login", "Success $response")
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