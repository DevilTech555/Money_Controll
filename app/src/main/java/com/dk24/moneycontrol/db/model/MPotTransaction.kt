package com.dk24.moneycontrol.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Money_Pot_Transaction")
data class MPotTransaction(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var amount: Float,
    var timestamp: Long,
    var moneyPotId: Long
)
