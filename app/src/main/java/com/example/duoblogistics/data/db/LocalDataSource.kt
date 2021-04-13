package com.example.duoblogistics.data.db

import com.example.duoblogistics.data.db.daos.TripDao
import com.example.duoblogistics.data.db.entities.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.concurrent.Flow

interface LocalDataSource {
    fun getTrips(): Flowable<List<Trip>>

    fun saveTrips(trips: List<Trip>): Single<List<Long>>

    fun getTripStoredItems(id: String): Flowable<List<StoredItem>>

    fun saveStoredItems(storedItems: List<StoredItem>): Single<List<Long>>

    fun saveStoredItemInfos(infos: List<StoredItemInfo>): Single<List<Long>>

    fun getStoredItemInfo(id: String): Maybe<StoredItemInfo>

    fun updateStoredItem(storedItem:StoredItem):Completable

    fun getStoredItemsById(ids: List<String>): Single<List<StoredItem>>

    fun saveAction(action: Action): Single<Long>

    fun saveActionStoredItems(actionId:Long, storedItems: List<StoredItem>): Single<List<Long>>

    fun getActionWithStoredItems(id:Long): Single<ActionWithStoredItems>

    fun getActions(): Single<List<Action>>
}