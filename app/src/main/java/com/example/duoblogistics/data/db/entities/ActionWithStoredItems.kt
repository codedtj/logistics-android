package com.example.duoblogistics.data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ActionWithStoredItems(
    @Embedded
    val action: Action,

    @Relation(
        parentColumn = "id",
        entity = StoredItem::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ActionWithStoredItem::class,
            parentColumn = "actionId",
            entityColumn = "storedItemId"
        )
    )
    var storedItems: List<StoredItem>
)