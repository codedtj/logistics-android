package com.example.duoblogistics.data.db

import com.example.duoblogistics.data.db.daos.StoredItemDao
import com.example.duoblogistics.data.db.daos.StoredItemInfoDao
import com.example.duoblogistics.data.db.daos.TripDao
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemInfo
import com.example.duoblogistics.data.db.entities.StoredItemWithInfo
import com.example.duoblogistics.data.db.entities.Trip
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSourceImpl(
    private val tripDao: TripDao,
    private val storedItemDao: StoredItemDao,
    private val storedItemInfoDao: StoredItemInfoDao,
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
}