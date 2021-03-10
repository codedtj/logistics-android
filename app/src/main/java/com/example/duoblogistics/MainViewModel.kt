package com.example.duoblogistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duoblogistics.extensions.notifyObserver

class MainViewModel() : ViewModel() {
    val codes = MutableLiveData<MutableList<String>>()

    init {
        codes.postValue(mutableListOf())
    }

    fun pushCode(code: String) {
        if (codes.value?.find { c -> c == code } === null) {
            codes.value?.add(code)
            codes.notifyObserver()
        }
    }
}