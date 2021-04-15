package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.ActionWithEverything
import com.example.duoblogistics.data.db.entities.ActionWithStoredItems
import com.example.duoblogistics.data.db.entities.StoredItem
import io.reactivex.Single

interface ActionRepository {
    fun getActionWithStoredItems(actionId: Long): Single<ActionWithStoredItems>

//    fun getActionWithEverything(actionId: Long): Single<ActionWithEverything>

    fun saveActionWithStoredItems(action: Action, storedItems:List<StoredItem>): Single<List<Long>>

    fun getActions(): Single<List<Action>>
}