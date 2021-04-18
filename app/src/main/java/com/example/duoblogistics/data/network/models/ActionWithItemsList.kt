package com.example.duoblogistics.data.network.models

data class ActionWithItemsList(
    val action:String,
    val tripId:String,
    val targetTripId:String?,
    val branchId:String?,
    val storedItems:List<String>
)
