package com.example.duoblogistics.data.db.daos

import androidx.room.*
import com.example.duoblogistics.data.db.entities.StoredItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface StoredItemDao {
    @Query("SELECT * from stored_items WHERE trip_id= :trip_id")
    fun getTripStoredItems(trip_id:String): Flowable<List<StoredItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(storedItems: List<StoredItem>): Single<List<Long>>

    @Update
    fun update(storedItem: StoredItem): Completable
}