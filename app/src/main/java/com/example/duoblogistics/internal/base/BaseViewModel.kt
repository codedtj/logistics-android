package com.example.duoblogistics.internal.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duoblogistics.internal.data.ExceptionModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    val exception = MutableLiveData<ExceptionModel>()

    protected val compositeDisposable = CompositeDisposable()

    fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        clearDisposables()
    }

}