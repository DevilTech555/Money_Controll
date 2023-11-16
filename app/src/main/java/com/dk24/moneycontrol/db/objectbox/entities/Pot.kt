package com.dk24.moneycontrol.db.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Pot(
    @Id
    var id: Long = 0,
    var name: String? = null,
    var totalAmount: Long? = null,
    var savedAmount: Long? = null,
    var isCompleted: Boolean? = null
)
