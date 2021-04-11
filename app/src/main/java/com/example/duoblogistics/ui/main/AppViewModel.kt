package com.example.duoblogistics.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duoblogistics.data.db.entities.Code
import com.example.duoblogistics.extensions.notifyObserver

class AppViewModel() : ViewModel() {
    private val mCode = MutableLiveData<String>()
    val code: LiveData<String>
        get() = mCode

    fun pushCode(code: String) {
        val current = mCode.value
        if (current != code) {
            mCode.postValue(code)
        }
    }
}