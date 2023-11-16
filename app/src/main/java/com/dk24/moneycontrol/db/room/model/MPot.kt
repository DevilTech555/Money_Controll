package com.dk24.moneycontrol.db.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Money_Pot")
data class MPot(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var totalAmount: Float,
    var savedAmount: Float,
    var isCompleted: Boolean
)
