package com.example.duoblogistics.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.Branch
import com.example.duoblogistics.data.network.RemoteDataSource
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.schedulers.Schedulers

class AppViewModel(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseViewModel() {
    private val mCode = MutableLiveData<String>()
    val code: LiveData<String>
        get() = mCode

    fun pushCode(code: String) {
        val current = mCode.value
        if (current != code) {
            mCode.postValue(code)
        }
    }

    fun getBranches() {
        compositeDisposable += remoteDataSource.fetchBranches()
            .switchMapSingle {
                Log.d("app-vm", "Branches are fetched $it")
                localDataSource.saveBranches(it)
            }
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    Log.d("app-vm", "Branches are saved $it")
                },
                {
                    Log.e("app-vm", "Failed to save branches $it")
                }
            )
    }
}