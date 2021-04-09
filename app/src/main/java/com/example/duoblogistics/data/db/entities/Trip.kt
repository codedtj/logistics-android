package com.example.duoblogistics.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import org.jetbrains.annotations.NotNull

@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey
    val id: String,
    @Json(name = "carId")
    val carId: String = "",
    @Json(name = "code")
    val code: String = "",
    @Json(name = "departureAt")
    val departureAt: String? = null,
    @Json(name = "departureDate")
    val departureDate: String? = null,
    @Json(name = "returnDate")
    val returnDate: String? = null,
    @Json(name = "returnedAt")
    val returnedAt: String? = null,
    @Json(name = "status")
    val status: String = ""
)