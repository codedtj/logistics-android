package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.StoredItem
import io.reactivex.Completable

class StoredItemRepositoryImpl(private val local: LocalDataSource) : StoredItemRepository {
    override fun updateStoredItem(storedItem: StoredItem):Completable =
        local.updateStoredItem(storedItem)
}