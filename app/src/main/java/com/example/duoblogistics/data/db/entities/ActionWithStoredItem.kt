package com.example.duoblogistics.data.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["actionId", "storedItemId"])
data class ActionWithStoredItem(
    val actionId:Long,
    val storedItemId:String
)
