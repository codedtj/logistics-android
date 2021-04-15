package com.example.duoblogistics.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "branches")
data class Branch(
    @PrimaryKey
    val id: String,
    val name: String
)