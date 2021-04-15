package com.example.duoblogistics.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.network.RemoteDataSource

class AppViewModelFactory(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AppViewModel(remoteDataSource, localDataSource) as T
    }
}