package com.example.duoblogistics.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.duoblogistics.data.db.entities.Branch
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface BranchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(branches: List<Branch>): Single<List<Long>>

    @Query("SELECT * FROM branches")
    fun getBranches(): Single<List<Branch>>

    @Query("SELECT * FROM branches WHERE id=:id")
    fun getBranch(id:String):Single<Branch>
}