package com.example.duoblogistics.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duoblogistics.data.entities.Code
import com.example.duoblogistics.extensions.notifyObserver

class MainViewModel() : ViewModel() {
    val mCodes = MutableLiveData<ArrayList<Code>>()
    val codes: LiveData<ArrayList<Code>>
        get() = mCodes

    init {
        mCodes.postValue(arrayListOf())
    }

    fun pushCode(code: String) {
        if (mCodes.value?.find { c -> c.code == code } === null) {
            mCodes.value?.add(Code(null, code))
            mCodes.notifyObserver()
        }
    }

    fun removeCode(code: Code) {
        mCodes.value?.remove(code)
        mCodes.notifyObserver()
    }
}