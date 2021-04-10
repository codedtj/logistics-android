package com.example.duoblogistics.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemInfo
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface StoredItemInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(storedItemInfos: List<StoredItemInfo>): Single<List<Long>>

    @Query("SELECT * FROM stored_item_infos WHERE id=:id")
    fun getStoredItemInfo(id:String): Single<StoredItemInfo>
}