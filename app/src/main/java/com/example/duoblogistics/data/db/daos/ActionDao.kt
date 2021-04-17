package com.example.duoblogistics.data.db.daos

import androidx.room.*
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.ActionWithEverything
import com.example.duoblogistics.data.db.entities.ActionWithStoredItem
import com.example.duoblogistics.data.db.entities.ActionWithStoredItems
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ActionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(action: Action): Single<Long>

    @Update
    fun update(action:Action):Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertActionWithStoredItems(actionsWithStoredItems: List<ActionWithStoredItem>): Single<List<Long>>

    @Transaction
    @Query("SELECT * FROM actions WHERE id=:id")
    fun getActionWithStoredItems(id: Long): Single<ActionWithStoredItems>

    @Transaction
    @Query("SELECT * FROM actions WHERE status='pending'")
    fun getPendingActionsWithStoredItems(): Single<List<ActionWithStoredItems>>

    @Query("SELECT * FROM actions ORDER BY id DESC")
    fun getActions(): Single<List<Action>>

    @Query("UPDATE actions SET status='completed' WHERE id=:id")
    fun markActionCompleted(id:Long):Completable
}