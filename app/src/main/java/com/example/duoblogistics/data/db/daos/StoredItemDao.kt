package com.example.duoblogistics.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.duoblogistics.data.db.entities.StoredItem
import io.reactivex.Flowable

@Dao
interface StoredItemDao {
    @Query("SELECT * from stored_items WHERE trip_id= :trip_id")
    fun getTripStoredItems(trip_id:String): Flowable<List<StoredItem>>
}