package com.example.duoblogistics.ui.trips.actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.data.db.LocalDataSource

class SelectActionViewModelFactory(
    private val localDataSource: LocalDataSource
) :
    ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SelectActionViewModel(localDataSource) as T
        }
}