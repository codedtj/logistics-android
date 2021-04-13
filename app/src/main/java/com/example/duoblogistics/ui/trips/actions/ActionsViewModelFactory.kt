package com.example.duoblogistics.ui.trips.actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.data.repositories.ActionRepository

class ActionsViewModelFactory (private val actionRepository: ActionRepository   ) :
    ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActionsViewModel(actionRepository) as T
        }
}