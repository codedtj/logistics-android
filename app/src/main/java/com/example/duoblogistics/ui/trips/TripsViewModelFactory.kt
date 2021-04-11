package com.example.duoblogistics.ui.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.data.repositories.StoredItemRepository
import com.example.duoblogistics.data.repositories.TripsRepository

class TripsViewModelFactory(
    private val tripsRepository: TripsRepository,
    private val storedItemRepository: StoredItemRepository

    ) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TripsViewModel(tripsRepository, storedItemRepository) as T
    }
}