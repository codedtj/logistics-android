package com.example.duoblogistics.data.db.entities

import androidx.room.*

@Entity(
    tableName = "stored_items",
    foreignKeys = [
        ForeignKey(
            entity = Trip::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("trip_id"),
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
        ),
//        ForeignKey(
//            entity = StoredItemInfo::class,
//            parentColumns = arrayOf("id"),
//            childColumns = arrayOf("stored_item_info_id"),
//            onDelete = ForeignKey.NO_ACTION,
//            onUpdate = ForeignKey.CASCADE
//        )
                  ],
    indices = [
        Index(value = ["trip_id", "stored_item_info_id"])
    ]
)
data class StoredItem(
    @PrimaryKey
    val id: String,
    val code: String,
    val status: String,
    val stored_item_info_id: String,
    val trip_id: String,
    val trip_status: String,
    var scanned: Boolean = false
)


