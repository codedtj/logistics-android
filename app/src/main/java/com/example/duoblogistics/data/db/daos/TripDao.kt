package com.example.duoblogistics.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.duoblogistics.data.db.entities.Trip
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trips: List<Trip>): Single<List<Long>>

    @Query("SELECT * FROM trips")
    fun getTrips(): Flowable<List<Trip>>

    @Query("SELECT * FROM trips WHERE id=:id")
    fun getTrip(id:String): Single<Trip>

    @Query("DELETE FROM trips WHERE id NOT IN (:trips)")
    fun deleteTripsWhereNotIn(trips: List<String>): Completable
}