package com.example.duoblogistics.data.db.daos

import androidx.room.*
import com.example.duoblogistics.data.db.entities.StoredItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.Flow

@Dao
interface StoredItemDao {
    @Query("SELECT * from stored_items WHERE trip_id= :trip_id")
    fun getTripStoredItems(trip_id:String): Flowable<List<StoredItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(storedItems: List<StoredItem>): Single<List<Long>>

    @Update
    fun update(storedItem: StoredItem): Completable

    @Query("SELECT * FROM stored_items WHERE id  IN (:storedItems)")
    fun getStoredItemsWhereIdIn(storedItems: List<String>): Single<List<StoredItem>>
}