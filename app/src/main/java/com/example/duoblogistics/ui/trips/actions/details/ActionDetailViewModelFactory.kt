package com.example.duoblogistics.ui.trips.actions.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.repositories.ActionRepository

class ActionDetailViewModelFactory(
    private val actionRepository: ActionRepository,
    private val localDataSource: LocalDataSource
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ActionDetailViewModel(actionRepository, localDataSource) as T
    }
}