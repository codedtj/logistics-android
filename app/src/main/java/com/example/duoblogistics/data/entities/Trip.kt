package com.example.duoblogistics.data.entities

import com.squareup.moshi.Json


data class Trip(
    @Json(name = "carId")
    val carId: String = "",
    @Json(name = "code")
    val code: String = "",
    @Json(name = "departureAt")
    val departureAt: String = "",
    @Json(name = "departureDate")
    val departureDate: String = "",
    @Json(name = "id")
    val id: String = "",
    @Json(name = "returnDate")
    val returnDate: String = "",
    @Json(name = "returnedAt")
    val returnedAt: Any = Any(),
    @Json(name = "status")
    val status: String = ""
)