package com.example.duoblogistics.data.db

import android.util.Log
import com.example.duoblogistics.data.db.daos.ActionDao
import com.example.duoblogistics.data.db.daos.StoredItemDao
import com.example.duoblogistics.data.db.daos.StoredItemInfoDao
import com.example.duoblogistics.data.db.daos.TripDao
import com.example.duoblogistics.data.db.entities.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class LocalDataSourceImpl(
    private val tripDao: TripDao,
    private val storedItemDao: StoredItemDao,
    private val storedItemInfoDao: StoredItemInfoDao,
    private val actionDao: ActionDao
) : LocalDataSource {
    override fun getTrips(): Flowable<List<Trip>> = tripDao.getTrips()

    override fun saveTrips(trips: List<Trip>): Single<List<Long>> {
        return tripDao.deleteTripsWhereNotIn(trips.map { it.id }).andThen(tripDao.insert(trips))
    }

    override fun getTripStoredItems(id: String): Flowable<List<StoredItem>> =
        storedItemDao.getTripStoredItems(id)

    override fun saveStoredItems(storedItems: List<StoredItem>): Single<List<Long>> =
        storedItemDao.insert(storedItems)

    override fun saveStoredItemInfos(infos: List<StoredItemInfo>): Single<List<Long>> =
        storedItemInfoDao.insert(infos)

    override fun getStoredItemInfo(id: String): Maybe<StoredItemInfo> =
        storedItemInfoDao.getStoredItemInfo(id)

    override fun updateStoredItem(storedItem: StoredItem): Completable =
        storedItemDao.update(storedItem)

    override fun getStoredItemsById(ids: List<String>): Single<List<StoredItem>> =
        storedItemDao.getStoredItemsWhereIdIn(ids)

    override fun saveAction(action: Action): Single<Long> = actionDao.insert(action)

    override fun saveActionStoredItems(
        actionId: Long,
        storedItems: List<StoredItem>
    ): Single<List<Long>> = actionDao.insertActionWithStoredItems(storedItems.map {
        ActionWithStoredItem(actionId, it.id)
    })

    override fun getActionWithStoredItems(id:Long): Single<ActionWithStoredItems> =
        actionDao.getActionWithStoredItems(id)

    override fun getActions(): Single<List<Action>> = actionDao.getActions()
}