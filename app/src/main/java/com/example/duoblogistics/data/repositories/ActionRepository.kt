package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.ActionWithStoredItems
import com.example.duoblogistics.data.db.entities.StoredItem
import io.reactivex.Single

interface ActionRepository {
    fun getActionWithStoredItems(actionId: Long): Single<ActionWithStoredItems>

    fun saveActionWithStoredItems(action: Action, storedItems:List<StoredItem>): Single<List<Long>>
}