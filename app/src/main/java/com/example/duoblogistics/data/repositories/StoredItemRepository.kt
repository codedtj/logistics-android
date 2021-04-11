package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.db.entities.StoredItem
import io.reactivex.Completable

interface StoredItemRepository {
    fun updateStoredItem(storedItem: StoredItem):Completable
}