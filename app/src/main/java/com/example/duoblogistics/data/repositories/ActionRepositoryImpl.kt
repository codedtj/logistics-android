package com.example.duoblogistics.data.repositories

import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.ActionWithStoredItems
import com.example.duoblogistics.data.db.entities.StoredItem
import io.reactivex.Single

class ActionRepositoryImpl(
    private val localDataSource: LocalDataSource
) : ActionRepository {
    override fun getActionWithStoredItems(actionId: Long): Single<ActionWithStoredItems> {
        return localDataSource.getActionWithStoredItems(actionId)
    }

    override fun saveActionWithStoredItems(
        action: Action,
        storedItems: List<StoredItem>
    ): Single<List<Long>> {
        return localDataSource.saveAction(action)
            .flatMap {
                localDataSource.saveActionStoredItems(it, storedItems)
            }
    }

    override fun getActions(): Single<List<Action>> = localDataSource.getActions()
}