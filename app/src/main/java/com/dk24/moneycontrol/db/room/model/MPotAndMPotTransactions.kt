package com.dk24.moneycontrol.db.room.model

import androidx.room.Embedded
import androidx.room.Relation

data class MPotAndMPotTransactions(
    @Embedded
    val mPot: MPot,
    @Relation(
        parentColumn = "id",
        entityColumn = "moneyPotId"
    )
    val mPotTransaction: List<MPotTransaction>
)
