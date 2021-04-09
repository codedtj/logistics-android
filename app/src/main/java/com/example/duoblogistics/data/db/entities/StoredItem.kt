package com.example.duoblogistics.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "stored_items",
    foreignKeys = [ForeignKey(
        entity = Trip::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("trip_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["trip_id", "stored_item_info_id"])
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
    val scanned:Boolean = false
)
