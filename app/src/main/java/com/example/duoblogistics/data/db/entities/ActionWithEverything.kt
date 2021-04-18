package com.example.duoblogistics.data.db.entities

import androidx.room.Embedded

data class ActionWithEverything(
    @Embedded
    val action: Action,

    val storedItems: List<StoredItem>,

    var trip: Trip? = null,

    var targetTripId: Trip? = null,

    var branch: Branch? = null
)