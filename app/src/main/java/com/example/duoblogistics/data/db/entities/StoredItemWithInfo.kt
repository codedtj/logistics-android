package com.example.duoblogistics.data.db.entities


data class StoredItemWithInfo(
    val id: String,
    val code: String,
    val status: String,
    val stored_item_info_id: String,
    val trip_id: String,
    val trip_status: String,
    val scanned: Boolean = false,
    val info: StoredItemInfo
)