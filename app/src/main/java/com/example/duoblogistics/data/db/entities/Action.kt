package com.example.duoblogistics.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "actions"
)
data class Action(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val tripId: String,
    val tripToId: String? = null,
    val branchToId: String? = null,
    var status:String = "pending"
)