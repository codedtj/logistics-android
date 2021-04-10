package com.example.duoblogistics.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "stored_item_infos"
)
data class StoredItemInfo(
    @PrimaryKey
    val id: String,
    val shop: String,
    val count: Int,
    val weight: Double,
    val height: Double,
    val length: Double,
    val width: Double,
    val owner_code: String
)