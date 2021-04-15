package com.example.duoblogistics.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.duoblogistics.data.db.daos.*
import com.example.duoblogistics.data.db.entities.*

@Database(
    entities = [
        Trip::class,
        StoredItem::class,
        StoredItemInfo::class,
        ActionWithStoredItem::class,
        Action::class,
        Branch::class
    ],
    version = 1
)
abstract class DuobLogisticsDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun storedItemDao(): StoredItemDao
    abstract fun storedItemInfoDao(): StoredItemInfoDao
    abstract fun actionDao(): ActionDao
    abstract fun branchDao(): BranchDao

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