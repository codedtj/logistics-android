package com.example.duoblogistics.data.network.models

data class ActionWithItemsList(
    val action:String,
    val tripId:String,
    val tripToId:String?,
    val branchToId:String?,
    val storedItems:List<String>
)
