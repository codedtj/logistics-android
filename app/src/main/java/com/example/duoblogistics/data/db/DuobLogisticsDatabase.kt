package com.example.duoblogistics.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.duoblogistics.data.db.daos.StoredItemDao
import com.example.duoblogistics.data.db.daos.StoredItemInfoDao
import com.example.duoblogistics.data.db.daos.TripDao
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.StoredItemInfo
import com.example.duoblogistics.data.db.entities.Trip

@Database(entities = [Trip::class, StoredItem::class, StoredItemInfo::class], version = 1)
abstract class DuobLogisticsDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun storedItemDao(): StoredItemDao
    abstract fun storedItemInfoDao(): StoredItemInfoDao

    companion object {
        @Volatile
        private var instance: DuobLogisticsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DuobLogisticsDatabase::class.java, "gulgunchik.db"
            )
                .build()
    }
}